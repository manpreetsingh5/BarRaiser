package com.aquamarine.barraiser.service.equipment.interfaces;


import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.enums.MeasurementEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface EquipmentService {
    boolean addEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    boolean editEquipment(EquipmentDTO equipmentDTO, MultipartFile multipartFile) throws IOException;
    boolean deleteEquipment(int equipment_id);
    Map<String, Object> getEquipmentById(int id) throws IOException;
    Set<Map<String, Object>> viewEquipmentByUser(String email) throws IOException;
    Set<Map<String, Object>> viewIngredientsByUser(String email) throws IOException;
    Set<Map<String, Object>> viewAllEquipment() throws IOException;
    Set<Map<String, Object>> viewAllIngredients() throws IOException;

}

