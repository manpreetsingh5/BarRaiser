package com.aquamarine.barraiser.service.drink.implementation;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
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

    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public Drink addDrink(DrinkDTO drinkDTO) {
        Optional <User> user = userRepository.findById(drinkDTO.getAdded_by());
        Drink drink = new Drink();

        if (user.isPresent()){
             drink = Drink.builder().added_by(user.get())
                    .id(drinkDTO.getId())
                    .image_path(drinkDTO.getImage_path())
                    .name(drinkDTO.getName())
                    .isPublic(drinkDTO.isPublic())
                    .build();

            drink = drinkRepository.save(drink);
        }
        return drink;
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
            if (drink.getAdded_by().getId() == id){
                DrinkDTO drinkDTO = drinkDTOMapper.toDrinkDTO(drink);
                drinksById.add(drinkDTO);
            }
        }

        return drinksById;
    }

    @Override
    public void editDrink(DrinkDTO drink) {
        Drink drink1 = drinkRepository.findById(drink.getId()).get();

        if (drink.getAdded_by() == drink1.getAdded_by().getId()){
            drink1.setName(drink.getName());
            drink1.setImage_path(drink.getImage_path());
            drink1.setPublic(drink.isPublic());
        }

        drinkRepository.save(drink1);
    }

}
