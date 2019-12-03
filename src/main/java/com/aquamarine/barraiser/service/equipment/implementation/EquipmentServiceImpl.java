package com.aquamarine.barraiser.service.equipment.implementation;

import com.aquamarine.barraiser.dto.mapper.EquipmentDTOMapper;
import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.enums.EquipmentEnum;
import com.aquamarine.barraiser.model.Equipment;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.EquipmentRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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


    private EquipmentDTOMapper equipmentDTOMapper = new EquipmentDTOMapper();

    @Autowired
    private ImageService imageService;


    @Value("images/equipment/")
    private String sub_folder;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public int addEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException{
        String fileName = equipmentDTO.getName();
        File file = imageService.convertMultiPartToFile(multipartFile, fileName);
        imageService.uploadFileToS3bucket(sub_folder+fileName, file);

        Optional<User> user = userRepository.findByEmail(equipmentDTO.getCreatedBy());

        if (user.isPresent()){
            Equipment equipment = new Equipment()
                    .setImage_path(equipmentDTO.getImage_path())
                    .setDescription(equipmentDTO.getDescription())
                    .setPublic(equipmentDTO.isPublic())
                    .setName(equipmentDTO.getName())
                    .setType(equipmentDTO.getType());

            equipmentRepository.save(equipment);
            return equipment.getId();
        }
        return -1;
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
    public void deleteEquipment(int equipment_id) {
        if (equipmentRepository.findById(equipment_id).isPresent()) {
            Equipment equipment = equipmentRepository.findById(equipment_id).get();
            imageService.deleteFileFromS3bucket(equipment.getImage_path());
            equipmentRepository.delete(equipment);
        }
    }

    @Override
    public Map<String, Object> getEquipmentById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        EquipmentDTO equipmentDTO = equipmentDTOMapper.toEquipmentDTO(equipmentRepository.findById(id).get());
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
        List<Equipment> drinks = equipmentRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Equipment e: drinks){
            if (e.isPublic() && e.getType() == EquipmentEnum.EQUIPMENT){
                res.add(getEquipmentById(e.getId()));
            }
        }

        return res;

    }

    @Override
    public Set<Map<String, Object>> viewAllIngredients() throws IOException {
        List<Equipment> drinks = equipmentRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Equipment e: drinks){
            if (e.isPublic() && e.getType() == EquipmentEnum.INGREDIENT){
                res.add(getEquipmentById(e.getId()));
            }
        }

        return res;
    }

    @Override
    public Set<Map<String, Object>> viewEquipmentByUser(String email) throws IOException {

        User user = userRepository.findByEmail(email).get();
        Set<Map<String, Object>> res = new HashSet<>();
        Set<Equipment> drinks = equipmentRepository.findAllByCreatedBy(user.getEmail());
        for (Equipment e : drinks) {
            res.add(getEquipmentById(e.getId()));
        }

        return res;
    }




}
