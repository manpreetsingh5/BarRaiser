package com.aquamarine.barraiser.service.cohort.interfaces;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface CohortService {
    int createCohort(CohortDTO cohortdto) throws IOException;
    void addUserToCohort(int cohort_id, int user_id);
    Set<UserDTO> getCohortUsers(int cohort_id);
    Set<CohortDTO> getUserCohorts(int user_id);
    CohortDTO findById(int id);
    UserDTO deleteStudentFromCohort(int cohort_id, int user_id);
    void deleteCohort(CohortDTO cohortDTO);
    ResponseEntity<byte[]> getCohortPicture(CohortDTO cohortDTO) throws IOException;

}
