package com.aquamarine.barraiser.service.equipment.interfaces;


import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface EquipmentService {
    int addEquipment(EquipmentDTO equipmentDTO);
    EquipmentDTO getDrinkById(int id);
    List<EquipmentDTO> getAllEquipment();
    Set<EquipmentDTO> getEquipmentByAddedBy(int user_id);
    void editEquipment(EquipmentDTO equipmentDTO);
    void deleteEquipment(EquipmentDTO equipmentDTO);
    ResponseEntity<byte[]> getEquipmentPicture(EquipmentDTO equipmentDTO) throws IOException;
}

