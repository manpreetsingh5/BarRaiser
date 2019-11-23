package com.aquamarine.barraiser.service.drink.implementation;

import com.aquamarine.barraiser.dto.mapper.CohortDTOMapper;
import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class DrinkServiceImp implements DrinkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ImageService imageService;

    @Value("images/drinks/")
    private String sub_folder;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public Drink addDrink(DrinkDTO drinkDTO, MultipartFile multipartFile) {
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
    public Map<String, Object> findDrinkById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        DrinkDTO drinkDTO = DrinkDTOMapper.toDrinkDTO(drinkRepository.findById(id).get());
        ret.put("cohort", drinkDTO);
        InputStream in = imageService.downloadFileFromS3bucket(bucketName, drinkDTO.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();
        ret.put("file", Base64.getEncoder().encode(imageBytes));
        return ret;
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