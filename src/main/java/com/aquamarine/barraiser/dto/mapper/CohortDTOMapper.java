package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.model.Cohort;

public class CohortDTOMapper {
    public static CohortDTO toCohortDTO(Cohort cohort) {
        return new CohortDTO()
                .setId(cohort.getId())
                .setDescription(cohort.getDescription())
                .setName(cohort.getName())
                .setImage_path(cohort.getImage_path())
                .setCreatedBy(cohort.getCreatedBy())
                .setCreatedDate(cohort.getCreatedDate())
                .setInstructor(cohort.getInstructor().getId())
                .setUser(cohort.getUser());
    }
}