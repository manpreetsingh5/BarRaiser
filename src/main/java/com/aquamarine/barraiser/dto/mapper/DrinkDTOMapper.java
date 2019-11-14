package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;


public class DrinkDTOMapper {
    public DrinkDTO toDrinkDTO(Drink drink) {
        return new DrinkDTO()
                .setId(drink.getId())
                .setAdded_by(drink.getAdded_by().getId())
                .setImage_path(drink.getImage_path())
                .setName(drink.getName())
                .setPublic(drink.isPublic());

    }
}
