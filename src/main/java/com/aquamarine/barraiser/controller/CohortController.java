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
    public void addCohort() {
        Map<String, Object> payload =  new HashMap<String, Object>();
        Set<User> set = new HashSet<>();
        set.add(userService.findUserById(2));
        set.add(userService.findUserById(3));
        payload.put("instructor", 1);
        payload.put("description", "Test Cohort");
        payload.put("user", set);


        Cohort cohort = Cohort.builder()
                .description((String) payload.get("description"))
                .instructor(userService.findUserById(1))
                .user(set)
                .build();


        CohortDTO cohortDTO = CohortDTOMapper.toCohortDTO(cohort);
//        cohortService.createCohort(cohortDTO);

        cohortService.deleteStudentFromCohort(cohortDTO, UserDTOMapper.toUserDTO(userService.findUserById(3)));

    }


}
