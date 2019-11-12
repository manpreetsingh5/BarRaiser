package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.service.user.interfaces.DrinkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/drink")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @RequestMapping(value = "/addDrink", method = RequestMethod.POST)
    public @ResponseBody String addNewDrink (@RequestBody Drink drink) {
        DrinkDTO drinkDTO = DrinkDTO.builder().name(drink.name).image_path(drink.image_path).added_by(drink.added_by).edited_by(drink.edited_by).isPublic(drink.isPublic)
                .build();

        drinkService.addDrink(drinkDTO);

        return "Success\n";
    }

    @AllArgsConstructor
    static class Drink {
        private String name;
        private String image_path;
        private int added_by;
        private int edited_by;
        private boolean isPublic;

    }

    @RequestMapping(value = "/deleteDrink/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteDrink(@PathVariable int id){
        if (drinkService.deleteDrink(id)){
            return "Success\n";
        }
        else{
            return "You can't delete a drink that has not been added";
        }
    }

    // Will only return all public drinks
    @RequestMapping(value = "/viewAll", method = RequestMethod.GET)
    public @ResponseBody Iterable<DrinkDTO> viewAllDrinks(){
        return drinkService.viewAllDrinks();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public @ResponseBody Iterable<DrinkDTO> viewDrinksByBartender(@PathVariable int id){
        return drinkService.viewDrinksByUser(id);
    }

}
