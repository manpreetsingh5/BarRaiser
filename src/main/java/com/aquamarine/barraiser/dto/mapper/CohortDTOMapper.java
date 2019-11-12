package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.model.Cohort;

public class CohortDTOMapper {
    public static CohortDTO toCohortDTO(Cohort cohort) {
        return new CohortDTO()
                .setId(cohort.getId())
                .setDescription(cohort.getDescription())
                .setInstructor(cohort.getInstructor().getId())
                .setStudents(cohort.getUser());
    }
}