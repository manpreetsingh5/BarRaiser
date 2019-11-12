package com.aquamarine.barraiser.service.user.implementation;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.user.interfaces.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrinkServiceImp implements DrinkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public void addDrink(DrinkDTO drinkDTO) {
        Drink drink = Drink.builder().added_by(userRepository.findById(drinkDTO.getAdded_by()).get())
                .edited_by(userRepository.findById(drinkDTO.getEdited_by()).get())
                .id(drinkDTO.getId())
                .image_path(drinkDTO.getImage_path())
                .name(drinkDTO.getName())
                .isPublic(drinkDTO.isPublic())
                .build();

        drinkRepository.save(drink);
    }

    @Override
    public boolean deleteDrink(int id) {
        Optional <Drink> toDelete = drinkRepository.findById(id);
        if (!toDelete.isPresent()){
            return false;
        }
        else{
            drinkRepository.delete(toDelete.get());
        }
        return true;

    }

    @Override
    public List<DrinkDTO> viewAllDrinks() {
        List<Drink> drinks = drinkRepository.findAll();
        List<DrinkDTO> publicDrinks = new ArrayList<>();

        for (Drink drink: drinks){
            if (drink.isPublic()){
                DrinkDTO drinkDTO = drinkDTOMapper.toDrinkDTO(drink);
                publicDrinks.add(drinkDTO);
            }
        }

        return publicDrinks;

    }

    @Override
    public List<DrinkDTO> viewDrinksByUser(int id) {
        List<Drink> drinks = drinkRepository.findAll();
        List<DrinkDTO> drinksById = new ArrayList<>();

        for (Drink drink: drinks){
            if (drink.getId() == id){
                DrinkDTO drinkDTO = drinkDTOMapper.toDrinkDTO(drink);
                drinksById.add(drinkDTO);
            }
        }

        return drinksById;
    }


}
