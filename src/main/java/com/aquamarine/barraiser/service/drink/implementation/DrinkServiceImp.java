package com.aquamarine.barraiser.service.drink.implementation;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkServiceImp implements DrinkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Value("/drinks")
    private String sub_folder;

    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public Drink addDrink(DrinkDTO drinkDTO) {
        Drink drink = new Drink();
        drink.setImage_path(drinkDTO.getImage_path());
        drink.setName(drinkDTO.getName());
        drink.setPublic(drinkDTO.isPublic());

        return drinkRepository.save(drink);
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
    public List<DrinkDTO> viewDrinksByUser(String email) {
        List<Drink> drinks = drinkRepository.findAll();
        List<DrinkDTO> drinksById = new ArrayList<>();

        for (Drink drink: drinks){
            if (drink.getCreatedBy().equals(email)){
                DrinkDTO drinkDTO = drinkDTOMapper.toDrinkDTO(drink);
                drinksById.add(drinkDTO);
            }
        }

        return drinksById;
    }

    @Override
    public boolean editDrink(DrinkDTO drink) {
        Optional<Drink> drink1 = drinkRepository.findById(drink.getId());

        if (drink1.isPresent()){
            drink1.get().setName(drink.getName());
            drink1.get().setImage_path(drink.getImage_path());
            drink1.get().setPublic(drink.isPublic());
            drinkRepository.save(drink1.get());
            return true;
        }

        return false;
    }

}