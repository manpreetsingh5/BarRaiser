package com.aquamarine.barraiser.service.cohort.interfaces;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;

import java.util.Set;

public interface CohortService {
    int createCohort(CohortDTO cohortdto);
    void addUserToCohort(CohortDTO cohortDTO, UserDTO userdto);
    Set<UserDTO> getCohortUsers(CohortDTO cohortDTO);
    UserDTO deleteStudentFromCohort(CohortDTO cohortDTO, UserDTO userDTO);
    void deleteCohort(CohortDTO cohortDTO);

}
