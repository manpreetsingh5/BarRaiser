package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.dto.model.ProgressDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.model.User;

import java.util.HashSet;
import java.util.Set;

public class ProgressDTOMapper {

    public static ProgressDTO toProgressDTO(Progress progress) {
//        Set<CohortDTO> cohorts = new HashSet<>();
//        Set<DrinkDTO> drinks = new HashSet<>();
//        Set<UserDTO> users = new HashSet<>();
//
//        for (Cohort c: progress.getCohorts()) {
//            cohorts.add(CohortDTOMapper.toCohortDTO(c));
//        }
//
//        for (Drink d : progress.getDrinks()) {
//            drinks.add(DrinkDTOMapper.toDrinkDTO(d));
//        }
//
//        for (User u : progress.getUsers()) {
//            users.add(UserDTOMapper.toUserDTO(u));
//        }

        return new ProgressDTO()
                .setId(progress.getId())
                .setCohort(progress.getCohort().getId())
                .setDrink(progress.getDrink().getId())
                .setUser(progress.getUser().getId())
                .setStatus(progress.isStatus());
    }
}
