package com.aquamarine.barraiser.controller;


import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
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
@RequestMapping(path="/api/cohort")
public class CohortController {

    @Autowired
    private CohortService cohortService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/addCohort", method= RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> addCohort(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart CohortDTO cohortDTO) throws IOException {
        if (cohortService.createCohort(cohortDTO, multipartFile)) {
            return new ResponseEntity<>("Cohort added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cohort not added successfully", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(path = "/deleteCohort", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> deleteCohort(@RequestParam int cohort_id){
        if (cohortService.deleteCohort(cohort_id)){
            return new ResponseEntity<>("Cohort deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cohort not deleted successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/editCohort", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> editCohort(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart CohortDTO cohortDTO) throws IOException{
        if (cohortService.editCohort(cohortDTO, multipartFile)){
            return new ResponseEntity<>("Cohort edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cohort not edited successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/addTrainee", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> addTraineeToCohort( @RequestParam  int cohort_id,  @RequestParam String traineeEmail) {
        if (cohortService.addUserToCohort(cohort_id, traineeEmail)){
            return new ResponseEntity<>("Trainee added to cohort successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Trainee not added to cohort successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/deleteTrainee", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> deleteTraineeFromCohort( @RequestParam  int cohort_id,  @RequestParam int user_id) {
        if (cohortService.deleteStudentFromCohort(cohort_id, user_id)){
            return new ResponseEntity<>("Trainee deleted from cohort successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Trainee not deleted from cohort successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/getTraineesInCohort", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> viewTrainees(@RequestParam int cohort_id) throws IOException {
        if (cohortService.findById(cohort_id) == null){
            return new ResponseEntity<>("Invalid cohort id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cohortService.getCohortUsers(cohort_id), HttpStatus.OK);
    }

    @RequestMapping(path="/getCohortForUser", method= RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<?> viewCohorts(@RequestParam int user_id) throws IOException {
        if (userService.findById(user_id) == null){
            return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cohortService.getUserCohorts(user_id), HttpStatus.OK);
    }

    @RequestMapping(path="/getCohort", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?>  getCohort(@RequestParam int cohort_id) throws IOException {
        if (cohortService.findById(cohort_id) == null){
            return new ResponseEntity<>("Invalid cohort id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cohortService.findById(cohort_id), HttpStatus.OK);
    }

    @RequestMapping(path="/addDrink", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> addDrinkToCohort( @RequestParam  int cohort_id,  @RequestParam int drink_id) {
        if (cohortService.addDrinkToCohort(cohort_id, drink_id)){
            return new ResponseEntity<>("Drink added to cohort successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not added to cohort successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/deleteDrink", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity<?> deleteDrinkFromCohort( @RequestParam  int cohort_id,  @RequestParam int drink_id) {
        if (cohortService.deleteDrinkFromCohort(cohort_id, drink_id)){
            return new ResponseEntity<>("Drink deleted from cohort successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not deleted from cohort successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path="/getDrinks", method= RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<?> getDrinksFromCohort( @RequestParam  int cohort_id) throws IOException {
        if (cohortService.findById(cohort_id) == null){
            return new ResponseEntity<>("Invalid cohort id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( cohortService.getDrinksFromCohort(cohort_id), HttpStatus.OK);
    }
}