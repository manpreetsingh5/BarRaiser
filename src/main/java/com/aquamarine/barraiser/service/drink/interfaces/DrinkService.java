package com.aquamarine.barraiser.service.drink.interfaces;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DrinkService {

    Drink addDrink(DrinkDTO drinkDTO, MultipartFile multipartFile) throws IOException;

    boolean deleteDrink(int id);

    List<DrinkDTO> viewAllDrinks() throws IOException;

    Set<Map<String, Object>> viewDrinksByUser(String email) throws IOException;

    Map<String, Object> findDrinkById(int id) throws IOException;

    boolean editDrink(DrinkDTO drink, MultipartFile multipartFile) throws IOException;
}
