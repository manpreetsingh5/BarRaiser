package com.aquamarine.barraiser.service.equipment.implementation;

import com.aquamarine.barraiser.dto.mapper.EquipmentDTOMapper;
import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.model.Equipment;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.EquipmentRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentDTOMapper equipmentDTOMapper;

    @Value("images/equipment/")
    private String sub_folder;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public int addEquipment(EquipmentDTO equipmentDTO) {
        Optional<User> user = userRepository.findByEmail(equipmentDTO.getCreatedBy());

        if (user.isPresent()){
            Equipment equipment = new Equipment()
                    .setImage_path(equipmentDTO.getImage_path())
                    .setDescription(equipmentDTO.getDescription())
                    .setPublic(equipmentDTO.isPublic())
                    .setType(equipmentDTO.getType());

            equipmentRepository.save(equipment);
            return equipment.getId();
        }
        return -1;
    }

    @Override
    public EquipmentDTO getDrinkById(int id) {
        return equipmentDTOMapper.toEquipmentDTO(equipmentRepository.findById(id).get());
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
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
    public Set<EquipmentDTO> getEquipmentByAddedBy(int user_id) {
        return null;
    }

    @Override
    public void editEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(equipmentDTO.getId()).get();

        if (equipmentDTO.getCreatedBy() == equipment.getCreatedBy() ){
            equipment.setDescription(equipmentDTO.getDescription());
            equipment.setImage_path(equipmentDTO.getImage_path());
            equipment.setPublic(equipmentDTO.isPublic());
        }

        equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipment(EquipmentDTO equipmentDTO) {
        int equipmentID = equipmentDTO.getId();
        if (equipmentRepository.findById(equipmentID).isPresent()) {
            equipmentRepository.delete(equipmentRepository.findById(equipmentID).get());
        }
    }

    @Override
    public ResponseEntity<byte[]> getEquipmentPicture(EquipmentDTO equipmentDTO) throws IOException {
        return null;
    }
}
