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

import java.util.List;

@RestController
@RequestMapping(path="/api/drink")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @RequestMapping(value = "/addDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> addNewDrink (@RequestBody DrinkDTO drink) {
        drinkService.addDrink(drink);
        return new ResponseEntity<>("Drink added successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/deleteDrink", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody ResponseEntity<?> deleteDrink(@RequestBody DrinkDTO drinkDTO){
        if (drinkService.deleteDrink(drinkDTO.getId())){
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

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody List<DrinkDTO> viewDrinksByBartender(@RequestParam String email){
        return drinkService.viewDrinksByUser(email);
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