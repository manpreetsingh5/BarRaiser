package com.aquamarine.barraiser.dto.model;

import com.aquamarine.barraiser.enums.MeasurementEnum;
import com.aquamarine.barraiser.model.Equipment;
import com.aquamarine.barraiser.model.Step;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepEquipmentDTO {

    private Integer id;

    private Step step;

    private Equipment equipment;

    private Double quantity;

    private MeasurementEnum unit;

}
