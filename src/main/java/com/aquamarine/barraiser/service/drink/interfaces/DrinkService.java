package com.aquamarine.barraiser.service.drink.interfaces;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DrinkService {

    Drink addDrink(DrinkDTO drinkDTO, MultipartFile multipartFile);

    boolean deleteDrink(int id);

    List<DrinkDTO> viewAllDrinks();

    List<DrinkDTO> viewDrinksByUser(String email);

    Map<String, Object> findDrinkById(int id) throws IOException;

    boolean editDrink(DrinkDTO drink);
}
