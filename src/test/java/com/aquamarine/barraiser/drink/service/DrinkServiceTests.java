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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaAuditing
public class DrinkServiceTests {

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private DrinkRepository drinkRepository;

    @MockBean
    private UserRepository userRepository;

    private DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    private Drink drink1 = new Drink();
    private Drink drink2 = new Drink();

    private User user1 = new User().setEmail("barack@whitehouse.gov")
                                    .setFirst_name("Barack")
                                    .setLast_name("Obama")
                                    .setId(33).setPassword("44").setStatus("BARTENDER");

    private User user2 = new User().setEmail("george@whitehouse.gov")
                                    .setFirst_name("George W.")
                                    .setLast_name("Bush")
                                    .setId(2).setPassword("43").setStatus("TRAINEE");

    private List <Drink> drinks = new ArrayList<>();

    @Before
    public void setup(){

        drink1.setName("Bourbon");
        drink1.setId(1);
        drink1.setImage_path("downloads/bourbon");
        drink1.setPublic(true);
        drink1.setCreatedBy("barack@whitehouse.gov");
        drink1.setCreatedDate(new Date());

        drink2.setName("Vodka");
        drink2.setId(2);
        drink2.setImage_path("downloads/vodka");
        drink2.setPublic(false);
        drink2.setCreatedBy("george@whitehouse.gov");
        drink2.setCreatedDate(new Date());

        drinks.add(drink1);
        drinks.add(drink2);

        Mockito.when(drinkRepository.findAll()).thenReturn(drinks);
        Mockito.when(drinkRepository.save(any(Drink.class))).thenReturn(drink1);
        Mockito.when(userRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(user1));
        Mockito.when(drinkRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(drink1));
        doNothing().when(drinkRepository).delete(any(Drink.class));

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
        String emailOfUser = "george@whitehouse.gov";

        List <DrinkDTO> drinksByBartender = drinkService.viewDrinksByUser(emailOfUser);

        assertNotNull(drinksByBartender);
        assertEquals(1, drinksByBartender.size());
        assertEquals("Vodka", drinksByBartender.get(0).getName());

    }

    @Test
    public void testAddDrink(){
        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setId(1);
        Drink newDrink = drinkService.addDrink(newDTO, any(MultipartFile.class));

        assertEquals(newDrink.getId(), drink1.getId());
        assertEquals(newDrink.getImage_path(), drink1.getImage_path());
        assertEquals(newDrink.getName(), drink1.getName());
        assertEquals(newDrink, drink1);

    }

    @Test
    public void testDeleteDrink(){
        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setId(1);
        Drink newDrink = drinkService.addDrink(newDTO, any(MultipartFile.class));
        boolean isAvailable;

        isAvailable = drinkService.deleteDrink(newDrink.getId());

        assertTrue(isAvailable);
    }

    @Test
    public void testDeleteDrink_notFound(){
        Mockito.when(drinkRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setId(1);
        Drink newDrink = drinkService.addDrink(newDTO, any(MultipartFile.class));
        boolean isAvailable;

        isAvailable = drinkService.deleteDrink(newDrink.getId());

        assertFalse(isAvailable);

    }

    @Test
    public void testEditDrink(){
        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setName("New Bourbon");
        newDTO.setImage_path("downloads/newbourbon");
        newDTO.setPublic(false);
        boolean successfulEdit;

        successfulEdit = drinkService.editDrink(newDTO);

        assertTrue(successfulEdit);

    }

    @Test
    public void testEditDrink_notFound(){
        Mockito.when(drinkRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        DrinkDTO newDTO = new DrinkDTO();
        newDTO.setName("New Bourbon");
        newDTO.setImage_path("downloads/newbourbon");
        newDTO.setPublic(false);
        boolean successfulEdit;

        successfulEdit = drinkService.editDrink(newDTO);

        assertFalse(successfulEdit);
    }

}