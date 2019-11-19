package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody Drink addNewDrink (@RequestBody DrinkDTO drink) {
        return drinkService.addDrink(drink);

        //return "Success\n";
    }


    @RequestMapping(value = "/deleteDrink", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody String deleteDrink(@RequestBody DrinkDTO drinkDTO){
        if (drinkService.deleteDrink(drinkDTO.getId())){
            return "Success\n";
        }
        else{
            return "You can't delete a drink that has not been added";
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
    public @ResponseBody Iterable<DrinkDTO> viewDrinksByBartender(@RequestBody DrinkDTO drinkDTO){
//        return drinkService.viewDrinksByUser(drinkDTO.getCreatedBy());
        return null;

    }

    @RequestMapping(value = "/editDrink", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('BARTENDER')")
    public @ResponseBody String editDrink (@RequestBody DrinkDTO drink) {
        if (drinkService.editDrink(drink)){
            return "Success\n";
        }

        return "Could not edit";
    }


}