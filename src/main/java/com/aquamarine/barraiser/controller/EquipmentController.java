package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.dto.model.EquipmentDTO;
import com.aquamarine.barraiser.service.equipment.interfaces.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "/addEquipment", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> addEquipment (@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart EquipmentDTO equipment) throws IOException {
        equipmentService.addEquipment(equipment, multipartFile);
        return new ResponseEntity<>("Equipment added successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> deleteEquipment(@RequestParam int equipment_id){
        if (equipmentService.deleteEquipment(equipment_id)){
            return new ResponseEntity<>("Equipment deleted successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Equipment not deleted successfully", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/viewAll", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody List<EquipmentDTO> viewAllEquipment(){
        return equipmentService.getAllPublicEquipment();
    }

//    @RequestMapping(value = "/viewDrink", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('BARTENDER')")
//    public @ResponseBody List<EquipmentDTO> viewEquipmentByBartender(@RequestParam String email){
//        return equipmentService.getEquipmentByAddedBy(email);
//    }

    @RequestMapping(value = "/editEquipment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> editDrink (@RequestBody EquipmentDTO equipment) {
        if (equipmentService.editEquipment(equipment)){
            return new ResponseEntity<>("Equipment edited successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Equipment not edited successfully", HttpStatus.BAD_REQUEST);
    }




}
