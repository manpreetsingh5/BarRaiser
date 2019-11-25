package com.aquamarine.barraiser.service.drink.implementation;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
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
import java.io.File;
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


    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public Drink addDrink(DrinkDTO drinkDTO, MultipartFile multipartFile) throws IOException {
        String fileName = drinkDTO.getName();
        File file = imageService.convertMultiPartToFile(multipartFile, fileName);
        imageService.uploadFileToS3bucket(sub_folder+fileName, file);

        Drink drink = new Drink();
        drink.setImage_path(drinkDTO.getImage_path());
        drink.setName(drinkDTO.getName());
        drink.setPublic(drinkDTO.isPublic());
        drink.setDescription(drinkDTO.getDescription());

        return drinkRepository.save(drink);
    }

    @Override
    public void deleteDrink(int id) {
        if (drinkRepository.findById(id).isPresent()) {
            Drink drink = drinkRepository.findById(id).get();
            imageService.deleteFileFromS3bucket(drink.getImage_path());
            drinkRepository.delete(drink);
        }

    }

    @Override
    public Set<Map<String, Object>> viewAllDrinks() throws IOException {
        List<Drink> drinks = drinkRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Drink drink: drinks){
            if (drink.isPublic()){
                res.add(findDrinkById(drink.getId()));
            }
        }

        return res;

    }

    @Override
    public Set<Map<String, Object>> viewDrinksByUser(String email) throws IOException {

        User user = userRepository.findByEmail(email).get();
        Set<Map<String, Object>> res = new HashSet<>();
        Set<Drink> drinks = drinkRepository.findAllByCreatedBy(user.getEmail());
        for (Drink d : drinks) {
            res.add(findDrinkById(d.getId()));
        }

        return res;
    }

    @Override
    public Map<String, Object> findDrinkById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        DrinkDTO drinkDTO = DrinkDTOMapper.toDrinkDTO(drinkRepository.findById(id).get());
        ret.put("cohort", drinkDTO);
        InputStream in = imageService.downloadFileFromS3bucket(drinkDTO.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();
        ret.put("file", imageBytes);
        return ret;
    }

    @Override
    public boolean editDrink(DrinkDTO drinkDTO, MultipartFile multipartFile) throws IOException {
        Optional<Drink> drinkOptional = drinkRepository.findById(drinkDTO.getId());
        Drink drink;
        if (drinkOptional.isPresent()){
            drink = drinkOptional.get();

            drink.setName(drinkDTO.getName());
            drink.setImage_path(drinkDTO.getImage_path());
            drink.setPublic(drinkDTO.isPublic());
            drink.setDescription(drinkDTO.getDescription());

            String filePath = drink.getImage_path();
            File file = imageService.convertMultiPartToFile(multipartFile, drink.getName());
            imageService.deleteFileFromS3bucket(filePath);
            drink.setImage_path(sub_folder+drink.getName());
            filePath = drink.getImage_path();
            imageService.uploadFileToS3bucket(filePath, file);


            drinkRepository.save(drink);
            return true;
        }

        return false;
    }

}