package com.aquamarine.barraiser.drink.service;


import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrinkServiceTests {

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private DrinkRepository drinkRepository;

    @MockBean
    private UserRepository userRepository;

    private DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    private Drink drink1;
    private Drink drink2;

    private User user1 = User.builder().email("barack@whitehouse.gov").first_name("Barack").last_name("Obama")
                        .id(33).password("44").status("BARTENDER").build();

    private User user2 = User.builder().email("george@whitehouse.gov").first_name("George W.").last_name("Bush")
                        .id(2).password("43").status("TRAINEE").build();

    private List <Drink> drinks = new ArrayList<>();

    @Before
    public void setUp(){
        drink1 = Drink.builder().name("Bourbon").added_by(user1).id(1).image_path("downloads/bourbon").isPublic(true)
                .build();

        drink2 = Drink.builder().name("Vodka").added_by(user2).id(2).image_path("downloads/vodka").isPublic(false)
                .build();

        drinks.add(drink1);
        drinks.add(drink2);


        Mockito.when(drinkRepository.findAll()).thenReturn(drinks);
        Mockito.when(drinkRepository.save(any(Drink.class))).thenReturn(drink1);
        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(user1));
        Mockito.when(drinkRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(drink1));
    }

    @Test
    public void testFindAll_Public(){
        List<DrinkDTO> allPublicDrinks = drinkService.viewAllDrinks();

        assertNotNull(allPublicDrinks);
        assertEquals(1, allPublicDrinks.size());
        assertEquals("Bourbon", allPublicDrinks.get(0).getName());

    }

    @Test
    public void testFindByBartender(){
        List <DrinkDTO> drinksByBartender = drinkService.viewDrinksByUser(2);

        assertNotNull(drinksByBartender);
        assertEquals(1, drinksByBartender.size());
        assertEquals("Vodka", drinksByBartender.get(0).getName());

    }

    @Test
    public void testAddDrink(){
        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setId(1);
        Drink newDrink = drinkService.addDrink(newDTO);

        assertEquals(newDrink.getId(), drink1.getId());
        assertEquals(newDrink.getAdded_by(), drink1.getAdded_by());
        assertEquals(newDrink.getImage_path(), drink1.getImage_path());
        assertEquals(newDrink.getName(), drink1.getName());
        assertEquals(newDrink, drink1);

    }

    @Test
    public void testDeleteDrink(){
        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setId(1);
        Drink newDrink = drinkService.addDrink(newDTO);








    }








}
