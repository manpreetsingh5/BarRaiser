package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api/drink")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @RequestMapping(value = "/addDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> addNewDrink (@RequestPart(value = "file") MultipartFile multipartFile, @RequestPart DrinkDTO drink) {
        drinkService.addDrink(drink, multipartFile);
        return new ResponseEntity<>("Drink added successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/deleteDrink", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> deleteDrink(@RequestParam int drinkID){
        if (drinkService.deleteDrink(drinkID)){
            return new ResponseEntity<>("Drink deleted successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Drink not deleted successfully", HttpStatus.BAD_REQUEST);
        }
    }

    // Will only return all public drinks
    @RequestMapping(value = "/viewAll", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody List<DrinkDTO> viewAllDrinks(){
        return drinkService.viewAllDrinks();
    }

    @RequestMapping(value = "/viewDrink", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody Map<String, Object> viewDrinksByBartender(@RequestParam int id) throws IOException {
        return drinkService.findDrinkById(id);
    }

    @RequestMapping(value = "/editDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> editDrink (@RequestBody DrinkDTO drink) {
        if (drinkService.editDrink(drink)){
            return new ResponseEntity<>("Drink deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drink not deleted successfully", HttpStatus.BAD_REQUEST);
    }


}