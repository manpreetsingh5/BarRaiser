package com.aquamarine.barraiser.dto.model;

import com.aquamarine.barraiser.enums.ActionsEnum;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.StepEquipment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepDTO {

    private int id;

    private Drink drink;

    private Integer step_number;

    private String description;

    private ActionsEnum action;

    private Integer successAmount;

    private Set<StepEquipmentDTO> equipmentSet;


}
