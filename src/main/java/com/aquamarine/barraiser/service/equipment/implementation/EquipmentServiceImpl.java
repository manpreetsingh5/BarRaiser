package com.aquamarine.barraiser.service.equipment.implementation;

import com.aquamarine.barraiser.dto.mapper.EquipmentDTOMapper;
import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.EquipmentDTO;
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
    public EquipmentDTO getEquipmentById(int id) {
        return equipmentDTOMapper.toEquipmentDTO(equipmentRepository.findById(id).get());
    }

    @Override
    public List<EquipmentDTO> getAllPublicEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<EquipmentDTO> publicEquipment = new ArrayList<>();
//        Set<Map<String, Object>> res = new
        for (Equipment e: equipmentList ){
            if (e.isPublic()){
                EquipmentDTO equipmentDTO = equipmentDTOMapper.toEquipmentDTO(e);
                publicEquipment.add(equipmentDTO);

            }
        }

        return publicEquipment;
    }

    @Override
    public List<EquipmentDTO> getEquipmentByAddedBy(int user_id) {

        return new ArrayList<EquipmentDTO>();
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
    public Map<String, Object> getEquipmentByID(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        EquipmentDTO equipmentDTO = equipmentDTOMapper.toEquipmentDTO(equipmentRepository.findById(id).get());
        ret.put("cohort", equipmentDTO);
        InputStream in = imageService.downloadFileFromS3bucket(equipmentDTO.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();
        ret.put("file", imageBytes);
        return ret;
    }

    @Override
    public boolean deleteEquipment(int equipment_id) {
        if (equipmentRepository.findById(equipment_id).isPresent()) {
            equipmentRepository.delete(equipmentRepository.findById(equipment_id).get());
            return true;
        }

        return false;
    }

    @Override
    public ResponseEntity<byte[]> getEquipmentPicture(EquipmentDTO equipmentDTO) throws IOException {
        return null;
    }

}
