package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.StepDTO;
import com.aquamarine.barraiser.dto.model.StepEquipmentDTO;
import com.aquamarine.barraiser.model.Step;
import com.aquamarine.barraiser.model.StepEquipment;

import java.util.HashSet;
import java.util.Set;

public class StepDTOMapper {

        public static StepDTO toStepDTO(Step step) {

            Set<StepEquipmentDTO> set = new HashSet<>();

            StepDTO res = new StepDTO()
                    .setId(step.getId())
                    .setAction(step.getAction())
                    .setDescription(step.getDescription())
                    .setDrink(step.getDrink())
                    .setStep_number(step.getStep_number());

            for (StepEquipment s : step.getEquipmentSet()) {
                StepEquipmentDTO dto = StepEquipmentDTOMapper.toStepEquipmentDTO(s);
                set.add(dto);
            }

            res.setEquipmentSet(set);
            return res;
        }
}
