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

    @Autowired
    private ImageService imageService;

    @Value("${app.awsServices.endpoint}")
    private String endpointUrl;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Value("/cohorts")
    private String folder;

    @RequestMapping(path="/addCohort", method= RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity addCohort(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart CohortDTO cohortDTO) throws IOException {
        cohortService.createCohort(cohortDTO, multipartFile);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/deleteCohort", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity deleteCohort(@RequestParam int cohort_id){
        cohortService.deleteCohort(cohort_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/editCohort", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity editCohort(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart CohortDTO cohortDTO) throws IOException{
        cohortService.editCohort(cohortDTO, multipartFile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path="/addTrainee", method= RequestMethod.GET)
//    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity addTraineeToCohort( @RequestParam  int cohort_id,  @RequestParam String traineeEmail) {
        cohortService.addUserToCohort(cohort_id, traineeEmail);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path="/deleteTrainee", method= RequestMethod.GET)
//    @PreAuthorize("hasAuthority('BARTENDER')")
    public ResponseEntity deleteTraineeToCohort( @RequestParam  int cohort_id,  @RequestParam int user_id) {
        cohortService.deleteStudentFromCohort(cohort_id, user_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path="/getTraineesInCohort", method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public Set<UserDTO> viewTrainees(@RequestParam int cohort_id) {
        return cohortService.getCohortUsers(cohort_id);
    }

    @RequestMapping(path="/getCohortForUser", method= RequestMethod.GET)
//    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")

    public Set<Map<String, Object>> viewCohorts(@RequestParam int user_id) throws IOException {
        return cohortService.getUserCohorts(user_id);
    }

    @RequestMapping(path="/getCohortPicture", method = RequestMethod.GET)
//    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public ResponseEntity<byte[]> getCohortPicture(int cohort_id) throws IOException {
        System.out.println("HERE");
        return cohortService.getCohortPicture(cohort_id);
    }

    @RequestMapping(path="/getCohort", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCohort(@RequestParam int cohort_id) throws IOException {
        return cohortService.findById(cohort_id);
    }

}
