package com.aquamarine.barraiser.drink.controller;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DrinkControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DrinkService drinkService;

    @Autowired
    private UserRepository userRepository;

    private DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    private Drink drink1 = new Drink();
    private Drink drink2 = new Drink();

    private User user1 = new User().setEmail("manny@gmail.com")
            .setFirst_name("Manny")
            .setLast_name("Obama")
            .setId(33).setPassword("123456").setStatus("BARTENDER");

    private User user2 = new User().setEmail("george@whitehouse.gov")
            .setFirst_name("George W.")
            .setLast_name("Bush")
            .setId(2).setPassword("43").setStatus("TRAINEE");

    private List<Drink> drinks = new ArrayList<>();

    private List<DrinkDTO> drinksDTO = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();


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

        drinksDTO.add(drinkDTOMapper.toDrinkDTO(drink1));
        drinksDTO.add(drinkDTOMapper.toDrinkDTO(drink2));

        Mockito.when(drinkService.addDrink(any(DrinkDTO.class), any(MultipartFile.class))).thenReturn(drink1);
        Mockito.when(drinkService.deleteDrink(any(Integer.class))).thenReturn(true);
        Mockito.when(drinkService.viewAllDrinks()).thenReturn(drinksDTO);
        Mockito.when(drinkService.viewDrinksByUser(any(String.class))).thenReturn(Arrays.asList(drinkDTOMapper.toDrinkDTO(drink2)));
        Mockito.when(drinkService.editDrink(any(DrinkDTO.class))).thenReturn(true);
    }

    @Test
    public void testAddDrink() throws Exception {

        String jsonString = objectMapper.writeValueAsString(drink1);

        mvc.perform(post("/api/drink/addDrink")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNCIsImlhdCI6MTU3NDIxMDk4NywiZXhwIjoxNTc0ODE1Nzg3fQ.j7tJjpmDYUTHN1PLHL1uS304QZ10MoQkt-Av-__Ozn6-xxY7U4dct1kvABCnfwjWvOdphATMt47Fk5m6vYAqyA")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDrink() throws Exception{

        String jsonString = objectMapper.writeValueAsString(drink1);

        mvc.perform(delete("/api/drink/deleteDrink")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNCIsImlhdCI6MTU3NDIxMDk4NywiZXhwIjoxNTc0ODE1Nzg3fQ.j7tJjpmDYUTHN1PLHL1uS304QZ10MoQkt-Av-__Ozn6-xxY7U4dct1kvABCnfwjWvOdphATMt47Fk5m6vYAqyA")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewAllDrinks() throws Exception{

        mvc.perform(get("/api/drink/viewAll")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNCIsImlhdCI6MTU3NDIxMDk4NywiZXhwIjoxNTc0ODE1Nzg3fQ.j7tJjpmDYUTHN1PLHL1uS304QZ10MoQkt-Av-__Ozn6-xxY7U4dct1kvABCnfwjWvOdphATMt47Fk5m6vYAqyA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(drink1.getName())))
                .andExpect(jsonPath("$[1].name", is(drink2.getName())));
    }

    @Test
    public void testViewDrinksByBartender() throws Exception{

        mvc.perform(get("/api/drink/view?email=george@whitehouse.gov")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNCIsImlhdCI6MTU3NDIxMDk4NywiZXhwIjoxNTc0ODE1Nzg3fQ.j7tJjpmDYUTHN1PLHL1uS304QZ10MoQkt-Av-__Ozn6-xxY7U4dct1kvABCnfwjWvOdphATMt47Fk5m6vYAqyA")
                .contentType(MediaType.APPLICATION_JSON).content("george@whitehouse.gov"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(drink2.getName())));
    }

    @Test
    public void testEditDrinks() throws Exception{

        String jsonString = objectMapper.writeValueAsString(drink1);

        mvc.perform(post("/api/drink/editDrink")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNCIsImlhdCI6MTU3NDIxMDk4NywiZXhwIjoxNTc0ODE1Nzg3fQ.j7tJjpmDYUTHN1PLHL1uS304QZ10MoQkt-Av-__Ozn6-xxY7U4dct1kvABCnfwjWvOdphATMt47Fk5m6vYAqyA")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());

    }

}
