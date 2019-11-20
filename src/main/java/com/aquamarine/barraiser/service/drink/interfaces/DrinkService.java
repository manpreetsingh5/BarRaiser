package com.aquamarine.barraiser.service.drink.interfaces;

import com.aquamarine.barraiser.controller.DrinkController;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DrinkService {

    Drink addDrink(DrinkDTO drinkDTO);

    boolean deleteDrink(int id);

    List<DrinkDTO> viewAllDrinks();

    List<DrinkDTO> viewDrinksByUser(String email);

    boolean editDrink(DrinkDTO drink);
}
