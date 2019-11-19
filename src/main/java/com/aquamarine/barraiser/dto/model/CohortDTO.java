package com.aquamarine.barraiser.dto.model;

import com.aquamarine.barraiser.model.User;
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
public class CohortDTO  {
    private int id;

    private String name;

    private String description;

    private int instructor;

    private String image_path;

    private Set<User> user;

    private String createdBy;

    private Date createdDate;
}