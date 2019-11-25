package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.StepDTO;
import com.aquamarine.barraiser.model.Step;

public class StepDTOMapper {

        public static StepDTO toStepDTO(Step step) {
            return (StepDTO) new StepDTO()
                    .setId(step.getId())
                    .setAction(step.getAction())
                    .setDescription(step.getDescription())
                    .setDrink(step.getDrink())
                    .setEquipmentSet(step.getEquipmentSet())
                    .setStep_number(step.getStep_number());
        }
}
