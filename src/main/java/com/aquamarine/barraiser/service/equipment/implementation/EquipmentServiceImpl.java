package com.aquamarine.barraiser.service.equipment.implementation;

import com.aquamarine.barraiser.dto.mapper.EquipmentDTOMapper;
import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.enums.EquipmentEnum;
import com.aquamarine.barraiser.enums.MeasurementEnum;
import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.Equipment;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.EquipmentRepository;
import com.aquamarine.barraiser.repository.StepEquipmentRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private StepEquipmentRepository stepEquipmentRepository;


    private EquipmentDTOMapper equipmentDTOMapper = new EquipmentDTOMapper();

    @Autowired
    private ImageService imageService;


    @Value("images/equipment/")
    private String sub_folder;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public boolean addEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException{
        String fileName = equipmentDTO.getName();
        File file = imageService.convertMultiPartToFile(multipartFile, fileName);
        imageService.uploadFileToS3bucket(sub_folder+fileName, file);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()){
            Equipment equipment = new Equipment()
                    .setImage_path(sub_folder+fileName)
                    .setDescription(equipmentDTO.getDescription())
                    .setPublic(equipmentDTO.isPublic())
                    .setName(equipmentDTO.getName())
                    .setType(equipmentDTO.getType());

            equipmentRepository.save(equipment);
            return true;
        }
        return false;
    }

    @Override
    public boolean editEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentDTO.getId());

        if (equipmentOptional.isPresent() && equipmentDTO.getCreatedBy().equals(equipmentOptional.get().getCreatedBy())){
            Equipment equipment = equipmentOptional.get();
            equipment.setDescription(equipmentDTO.getDescription());
            equipment.setImage_path(equipmentDTO.getImage_path());
            equipment.setPublic(equipmentDTO.isPublic());
            equipment.setName(equipmentDTO.getName());

            String filePath = equipment.getImage_path();
            File file = imageService.convertMultiPartToFile(multipartFile, equipment.getName());
            imageService.deleteFileFromS3bucket(filePath);
            equipment.setImage_path(sub_folder+equipment.getName());
            filePath = equipment.getImage_path();
            imageService.uploadFileToS3bucket(filePath, file);
            equipmentRepository.save(equipment);
            return true;
        }

        return false;
    }


    @Override
    public boolean deleteEquipment(int equipment_id) {
        if (equipmentRepository.findById(equipment_id).isPresent()) {
            if (stepEquipmentRepository.findByEquipment(equipmentRepository.findById(equipment_id).get()) == null) {
                Equipment equipment = equipmentRepository.findById(equipment_id).get();
                imageService.deleteFileFromS3bucket(equipment.getImage_path());
                equipmentRepository.delete(equipment);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getEquipmentById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        Optional <Equipment> equipments = equipmentRepository.findById(id);
        if (!equipments.isPresent()){
            return null;
        }
        EquipmentDTO equipmentDTO = equipmentDTOMapper.toEquipmentDTO(equipments.get());
        ret.put("equipment", equipmentDTO);
        InputStream in = imageService.downloadFileFromS3bucket(equipmentDTO.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        if (imageFromAWS != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageFromAWS, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            in.close();
            ret.put("file", imageBytes);
        }
        else {
            ret.put("file", null);
        }
        return ret;

    }


    @Override
    public Set<Map<String, Object>> viewAllEquipment() throws IOException {
        List<Equipment> equipmentList = equipmentRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Equipment e: equipmentList){
            if (e.isPublic() && e.getType() == EquipmentEnum.EQUIPMENT){
                res.add(getEquipmentById(e.getId()));
            }
        }

        return res;
    }

    @Override
    public Set<Map<String, Object>> viewAllIngredients() throws IOException {
        List<Equipment> equipmentList = equipmentRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Equipment e: equipmentList){
            if (e.isPublic() && e.getType() == EquipmentEnum.INGREDIENT){
                res.add(getEquipmentById(e.getId()));
            }
        }

        return res;
    }

    @Override
    public Set<Map<String, Object>> viewEquipmentByUser(String email) throws IOException {
        Set<Map<String, Object>> res = new HashSet<>();
        Set<Equipment> drinks = equipmentRepository.findAllByCreatedByAndType(email, EquipmentEnum.EQUIPMENT);
        for (Equipment e : drinks) {
            res.add(getEquipmentById(e.getId()));
        }

        return res;
    }

    @Override
    public Set<Map<String, Object>> viewIngredientsByUser(String email) throws IOException {
        Set<Map<String, Object>> res = new HashSet<>();
        Set<Equipment> drinks = equipmentRepository.findAllByCreatedByAndType(email, EquipmentEnum.INGREDIENT);
        for (Equipment e : drinks) {
            res.add(getEquipmentById(e.getId()));
        }

        return res;
    }

    @Override
    public byte[] getEquipmentPicture(String image_path) throws IOException {
        InputStream in = imageService.downloadFileFromS3bucket(image_path).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();

        return imageBytes;
    }



}
