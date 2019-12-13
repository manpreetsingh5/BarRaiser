package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.enums.MeasurementEnum;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> addEquipment (@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart EquipmentDTO equipment) throws IOException {
        if(equipmentService.addEquipment(equipment, multipartFile)) {
            return new ResponseEntity<>("Equipment added successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Equipment not added successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> deleteEquipment(@RequestParam int equipment_id){
        if (equipmentService.deleteEquipment(equipment_id)){
            return new ResponseEntity<>("Equipment deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Equipment not deleted successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/viewEquipmentByID", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> viewEqupipmentByID(@RequestParam int equipment_id) throws IOException {
        if (equipmentService.getEquipmentById(equipment_id) == null){
            return new ResponseEntity<>("No equipment found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(equipmentService.getEquipmentById(equipment_id), HttpStatus.OK);
    }

    @RequestMapping(value = "/viewAllEquipment", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> viewAllEquipment() throws IOException {
        try {
            return new ResponseEntity<>(equipmentService.viewAllEquipment(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error viewing all equipment.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewAllIngredients", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> viewAllIngredients() throws IOException {
        try {
            return new ResponseEntity<>(equipmentService.viewAllIngredients(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error viewing all ingredients.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewUserEquipment", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> viewEquipmentByUser() throws IOException {
        return equipmentService.viewEquipmentByUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(value = "/viewUserIngredients", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> viewIngredientsByUser() throws IOException {
        return equipmentService.viewIngredientsByUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(value = "/editEquipment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> editDrink(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart EquipmentDTO equipment) throws IOException {
        if (equipmentService.editEquipment(equipment, multipartFile)){
            return new ResponseEntity<>("Equipment edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Equipment not edited successfully", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/viewUnits", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<MeasurementEnum[]> getUnits() {
        return new ResponseEntity<>( MeasurementEnum.values(), HttpStatus.OK);
    }

    @RequestMapping(value = "/viewPicture", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('BARTENDER', 'TRAINEE')")
    public @ResponseBody ResponseEntity<?> getEquipmentPicture(@RequestParam String image_path) throws IOException {
        try {
            byte[] imageBytes = equipmentService.getEquipmentPicture(image_path);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_PNG);
            httpHeaders.setContentLength(imageBytes.length);

            return new ResponseEntity<>(equipmentService.getEquipmentPicture(image_path), httpHeaders, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error viewing equipment image.", HttpStatus.BAD_REQUEST);
        }
    }

}
