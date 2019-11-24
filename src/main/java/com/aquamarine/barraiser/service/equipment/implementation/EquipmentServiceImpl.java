package com.aquamarine.barraiser.service.equipment.implementation;

import com.aquamarine.barraiser.dto.mapper.EquipmentDTOMapper;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public boolean editEquipment(EquipmentDTO equipmentDTO) {
        Optional<Equipment> equipment = equipmentRepository.findById(equipmentDTO.getId());

        if (equipment.isPresent() && equipmentDTO.getCreatedBy().equals(equipment.get().getCreatedBy())){
            equipment.get().setDescription(equipmentDTO.getDescription());
            equipment.get().setImage_path(equipmentDTO.getImage_path());
            equipment.get().setPublic(equipmentDTO.isPublic());
            equipment.get().setName(equipmentDTO.getName());
            equipmentRepository.save(equipment.get());
            return true;
        }

        return false;
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
