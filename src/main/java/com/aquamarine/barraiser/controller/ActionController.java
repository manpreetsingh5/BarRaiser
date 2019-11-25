package com.aquamarine.barraiser.controller;


import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.service.actions.interfaces.ActionService;
import com.aquamarine.barraiser.service.cohort.interfaces.CohortService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/api/actions")
public class ActionController {

    @Autowired
    private ActionService actionService;


    @RequestMapping(path="/getActions", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> getActions() throws IOException {
        return actionService.getAllActions();
    }



}