package com.aquamarine.barraiser.drink.service;


import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrinkServiceTests {

    @Autowired
    private DrinkService drinkService;

    @Mock
    private DrinkRepository drinkRepository;

    private Drink drink1;
    private Drink drink2;

    private User user1 = User.builder().email("barack@whitehouse.gov").first_name("Barack").last_name("Obama")
                        .id(1).password("44").status(false).build();

    private User user2 = User.builder().email("george@whitehouse.gov").first_name("George W.").last_name("Bush")
                        .id(2).password("43").status(false).build();

    private List <Drink> drinks = new ArrayList<>();

    @Before
    public void setUp(){
        drink1 = Drink.builder().name("Whiskey").added_by(user1).id(1).image_path("downloads/whiskey").isPublic(true)
                .build();

        drink2 = Drink.builder().name("Vodka").added_by(user2).id(2).image_path("downloads/vodka").isPublic(false)
                .build();

        drinks.add(drink1);
        //drinks.add(drink2);

        Mockito.when(drinkRepository.findAll()).thenReturn(drinks);
    }

    @Test
    public void testFindAll(){
        List<DrinkDTO> allPublicDrinks = drinkService.viewAllDrinks();

        assertNotNull(allPublicDrinks);
        assertEquals(2, allPublicDrinks.size());

    }





}
