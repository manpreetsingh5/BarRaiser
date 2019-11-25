package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.dto.model.StepDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Step;

import java.util.HashSet;


public class DrinkDTOMapper {
    public static DrinkDTO toDrinkDTO(Drink drink) {
        DrinkDTO drinkDTO = new DrinkDTO()
                .setId(drink.getId())
                .setImage_path(drink.getImage_path())
                .setName(drink.getName())
                .setDescription(drink.getDescription())
                .setPublic(drink.isPublic())
                .setCreatedBy(drink.getCreatedBy())
                .setCreatedDate(drink.getCreatedDate());

        System.out.println(drink.getSteps());
        HashSet<StepDTO> steps = new HashSet<>();
        for (Step s : drink.getSteps()) {
            System.out.println(s.getEquipmentSet());
            steps.add(StepDTOMapper.toStepDTO(s));
        }

        drinkDTO.setSteps(steps);

        return drinkDTO;

    }
}
