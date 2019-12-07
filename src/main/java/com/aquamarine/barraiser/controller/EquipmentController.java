package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public @ResponseBody Set<Map<String, Object>> viewAllEquipment() throws IOException {
        return equipmentService.viewAllEquipment();
    }

    @RequestMapping(value = "/viewAllIngredients", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> viewAllIngredients() throws IOException {
        return equipmentService.viewAllIngredients();
    }

    @RequestMapping(value = "/editEquipment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> editDrink(@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart EquipmentDTO equipment) throws IOException {
        if (equipmentService.editEquipment(equipment, multipartFile)){
            return new ResponseEntity<>("Equipment edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Equipment not edited successfully", HttpStatus.BAD_REQUEST);
    }

}
