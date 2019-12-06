package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.enums.ActionsEnum;
import com.aquamarine.barraiser.enums.MeasurementEnum;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Step;
import com.aquamarine.barraiser.model.StepEquipment;
import com.aquamarine.barraiser.repository.EquipmentRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path="/api/drink")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @RequestMapping(value = "/addDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> addNewDrink (@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart DrinkDTO drink) throws IOException {
        if (drinkService.addDrink(drink, multipartFile)){
            return new ResponseEntity<>("Drink added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not added successfully", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/deleteDrink", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> deleteDrink(@RequestParam int drinkID){
        if (drinkService.deleteDrink(drinkID)) {
            return new ResponseEntity<>("Drink deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not deleted successfully", HttpStatus.BAD_REQUEST);
    }

    // Will only return all public drinks and drinks user owns
    @RequestMapping(value = "/viewAll", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> viewAllDrinks() throws IOException {
        return drinkService.viewAllDrinks();
    }

    // Will return ONLY the particular user's drinks
    @RequestMapping(value = "/viewUserDrinks", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Set<Map<String, Object>> viewUserDrinks() throws IOException {
        return drinkService.viewDrinksByUser();
    }

    @RequestMapping(value = "/viewDrink", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?>  viewDrinkByID(@RequestParam int id) throws IOException {
        if (drinkService.findDrinkById(id) == null){
            return new ResponseEntity<>("No drinks found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(drinkService.findDrinkById(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/editDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> editDrink (@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart DrinkDTO drink) throws IOException {
        if (drinkService.editDrink(drink, multipartFile)){
            return new ResponseEntity<>("Equipment deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not deleted successfully", HttpStatus.BAD_REQUEST);
    }




}