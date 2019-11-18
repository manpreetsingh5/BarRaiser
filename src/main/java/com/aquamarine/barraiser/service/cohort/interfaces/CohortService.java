package com.aquamarine.barraiser.service.cohort.interfaces;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface CohortService {
    int createCohort(CohortDTO cohortdto, MultipartFile multipartFile) throws IOException;
    void addUserToCohort(CohortDTO cohortDTO, UserDTO userdto);
    Set<UserDTO> getCohortUsers(CohortDTO cohortDTO);
    Set<CohortDTO> getUserCohorts(UserDTO userDTO);
    Set<Object> findById(int id);
    UserDTO deleteStudentFromCohort(CohortDTO cohortDTO, UserDTO userDTO);
    void deleteCohort(CohortDTO cohortDTO);
    ResponseEntity<byte[]> getCohortPicture(CohortDTO cohortDTO) throws IOException;

}
