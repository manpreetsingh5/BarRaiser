package com.aquamarine.barraiser.controller;


import com.aquamarine.barraiser.ApiController;
import com.aquamarine.barraiser.dto.mapper.CohortDTOMapper;
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
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/cohort")
public class CohortController extends ApiController {

    @Autowired
    private CohortService cohortService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/addCohort}", method= RequestMethod.GET, headers = "Accept=application/json")
    public void addCohort() {
        Map<String, Object> payload =  new HashMap<String, Object>();
        payload.put("instructor", 1);
        payload.put("description", "Test Cohort");
        payload.put("user", new int[]{2, 3});


        Cohort cohort = Cohort.builder()
                .description((String) payload.get("description"))
                .instructor(userService.findUserById((Integer) payload.get("instructor")))
                .user((Set<User>) payload.get("user"))
                .build();

        cohortService.createCohort(CohortDTOMapper.toCohortDTO(cohort));

    }


}
