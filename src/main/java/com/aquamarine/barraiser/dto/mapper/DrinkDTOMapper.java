package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;


public class DrinkDTOMapper {
    public static DrinkDTO toDrinkDTO(Drink drink) {
        return (DrinkDTO) new DrinkDTO()
                .setId(drink.getId())
                .setImage_path(drink.getImage_path())
                .setName(drink.getName())
                .setPublic(drink.isPublic())
                .setCreatedBy(drink.getCreatedBy())
                .setCreatedDate(drink.getCreatedDate());

    }
}
