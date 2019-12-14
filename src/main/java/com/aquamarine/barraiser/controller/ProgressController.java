package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.ProgressDTO;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.service.progress.interfaces.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @RequestMapping(path="/addProgress", method= RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<?> addProgress(@RequestParam int cohort_id, @RequestParam int drink_id, @RequestParam int user_id){
        if (progressService.addProgress(cohort_id, drink_id, user_id)) {
            return new ResponseEntity<>("Progress added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not added successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/updateProgress", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> updateProgress(@RequestParam int cohort_id, @RequestParam int drink_id, @RequestParam int user_id){
        if (progressService.updateProgress(cohort_id, drink_id, user_id)) {
            return new ResponseEntity<>("Progress added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not added successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/getProgressByUser", method= RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<?> getProgressByUser(@RequestParam int user_id){
        Set<ProgressDTO> ret = progressService.getProgressByUser(user_id);
        if (ret != null) {
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not added successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/getProgressByCohort", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> getProgressByCohort(@RequestParam int cohort_id){
        Set<ProgressDTO> ret = progressService.getProgressByCohort(cohort_id);
        if (ret != null) {
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not added successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/getProgressByDrink", method= RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<?> getProgress(@RequestParam int drink_id, @RequestParam int user_id){
        ProgressDTO progressDTO = progressService.getProgressByDrink(drink_id, user_id);
        if (progressDTO != null) {
            return new ResponseEntity<>(progressDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not added successfully", HttpStatus.BAD_REQUEST);
    }


}
