package com.aquamarine.barraiser.service.equipment.interfaces;


import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface EquipmentService {
    int addEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    boolean editEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    void deleteEquipment(int equipment_id);
    Map<String, Object> getEquipmentById(int id) throws IOException;
    Set<Map<String, Object>> viewEquipmentByUser(String email) throws IOException;
    Set<Map<String, Object>> viewAllEquipment() throws IOException;
    Set<Map<String, Object>> viewAllIngredients() throws IOException;

}

