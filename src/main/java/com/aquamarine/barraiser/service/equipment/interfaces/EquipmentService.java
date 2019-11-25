package com.aquamarine.barraiser.service.equipment.interfaces;


import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EquipmentService {
    int addEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    EquipmentDTO getEquipmentById(int id);
    List<EquipmentDTO> getAllPublicEquipment();
    List<EquipmentDTO> getEquipmentByAddedBy(int user_id);
    Map<String, Object> getEquipmentByID(int id) throws IOException;
    boolean editEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    boolean deleteEquipment(int equipment_id);
    ResponseEntity<byte[]> getEquipmentPicture(EquipmentDTO equipmentDTO) throws IOException;
}

