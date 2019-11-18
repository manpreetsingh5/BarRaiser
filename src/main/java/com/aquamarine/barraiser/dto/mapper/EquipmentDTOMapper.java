package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.model.Equipment;

public class EquipmentDTOMapper {
    public EquipmentDTO toEquipmentDTO(Equipment equipment) {
        return new EquipmentDTO()
                .setId(equipment.getId())
                .setDescription(equipment.getDescription())
                .setImage_path(equipment.getImage_path())
                .setCreatedBy(equipment.getCreatedBy())
                .setCreatedDate(equipment.getCreatedDate());
    }
}
