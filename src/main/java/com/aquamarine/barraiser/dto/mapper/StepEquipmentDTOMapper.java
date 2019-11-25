package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.StepEquipmentDTO;
import com.aquamarine.barraiser.model.StepEquipment;

public class StepEquipmentDTOMapper {

    public static StepEquipmentDTO toStepEquipmentDTO(StepEquipment stepEquipment) {
        return (StepEquipmentDTO) new StepEquipmentDTO()
                .setId(stepEquipment.getId())
                .setStep(stepEquipment.getStep())
                .setQuantity(stepEquipment.getQuantity())
                .setUnit(stepEquipment.getUnit());
    }
}
