package com.aquamarine.barraiser.controller;


import com.aquamarine.barraiser.ApiController;
import com.aquamarine.barraiser.dto.mapper.CohortDTOMapper;
import com.aquamarine.barraiser.dto.mapper.UserDTOMapper;
import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.cohort.interfaces.CohortService;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/api/cohort")
public class CohortController {

    @Autowired
    private CohortService cohortService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/addCohort", method= RequestMethod.GET)
    public ResponseEntity addCohort(@RequestBody CohortDTO cohortDTO) {
        cohortService.createCohort(cohortDTO);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(path="/addTrainee", method= RequestMethod.GET)
    public void addTraineeToCohort(@RequestBody CohortDTO cohortDTO, @RequestBody UserDTO userDTO) {
        cohortService.addUserToCohort(cohortDTO, userDTO);
    }

    @RequestMapping(path="/deleteTrainee", method= RequestMethod.GET)
    public void deleteTraineeToCohort(@RequestBody CohortDTO cohortDTO, @RequestBody UserDTO userDTO) {
        cohortService.deleteStudentFromCohort(cohortDTO, userDTO);

    }

    @RequestMapping(path="/viewTrainees", method= RequestMethod.POST)
    public Set<UserDTO> viewTrainees(@RequestBody CohortDTO cohortDTO) {
        return cohortService.getCohortUsers(cohortDTO);
    }

    @RequestMapping(path="/viewCohorts", method= RequestMethod.POST)
    public Set<CohortDTO> viewCohorts(@RequestBody UserDTO userDTO) {
        return cohortService.getUserCohorts(userDTO);
    }

}
