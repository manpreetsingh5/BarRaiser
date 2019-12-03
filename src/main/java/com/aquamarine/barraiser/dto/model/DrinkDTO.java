package com.aquamarine.barraiser.dto.model;

import com.aquamarine.barraiser.model.Auditable;
import com.aquamarine.barraiser.model.Step;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrinkDTO extends Auditable<String> {
    private int id;

    private String name;

    private String description;

    private String image_path;

    private boolean isPublic;

    private String createdBy;

    private Date createdDate;

    private Set<StepDTO> steps;

}
