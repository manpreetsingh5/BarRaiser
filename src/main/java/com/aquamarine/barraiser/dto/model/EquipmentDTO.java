package com.aquamarine.barraiser.dto.model;

import com.aquamarine.barraiser.enums.EquipmentEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentDTO {
    private int id;

    private String name;

    private String description;

    private String image_path;

    private boolean isPublic;

    private EquipmentEnum type;

    private String createdBy;

    private Date createdDate;
}
