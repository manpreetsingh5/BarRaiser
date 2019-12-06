package com.aquamarine.barraiser.service.drink.implementation;

import com.aquamarine.barraiser.dto.mapper.DrinkDTOMapper;
import com.aquamarine.barraiser.dto.model.DrinkDTO;
import com.aquamarine.barraiser.dto.model.StepDTO;
import com.aquamarine.barraiser.dto.model.StepEquipmentDTO;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Step;
import com.aquamarine.barraiser.model.StepEquipment;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.StepEquipmentRepository;
import com.aquamarine.barraiser.repository.StepRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.drink.interfaces.DrinkService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private StepRepository stepRepository;

    @Autowired
    private StepEquipmentRepository stepEquipmentRepository;

    @Autowired
    private ImageService imageService;

    @Value("images/drinks/")
    private String sub_folder;


    DrinkDTOMapper drinkDTOMapper = new DrinkDTOMapper();

    @Override
    public boolean addDrink(DrinkDTO drinkDTO , MultipartFile multipartFile) throws IOException {
        String fileName = drinkDTO.getName();
        File file = imageService.convertMultiPartToFile(multipartFile, fileName);
        imageService.uploadFileToS3bucket(sub_folder+fileName, file);

        Drink drink = new Drink();

        drink.setImage_path(drinkDTO.getImage_path())
                .setName(drinkDTO.getName())
                .setPublic(drinkDTO.isPublic())
                .setDescription(drinkDTO.getDescription());
        HashSet<Step> steps = new HashSet<>();
        for (StepDTO stepdto: drinkDTO.getSteps()) {
            Step step = new Step()
                    .setDescription(stepdto.getDescription())
                    .setDrink(stepdto.getDrink())
                    .setAction(stepdto.getAction())
                    .setSuccessAmount(stepdto.getSuccessAmount())
                    .setStep_number(stepdto.getStep_number());

            steps.add(step);

            HashSet<StepEquipment> stepEquipments = new HashSet<>();
            for (StepEquipmentDTO stepEquipmentDTO: stepdto.getEquipmentSet()) {
                StepEquipment stepEquipment = new StepEquipment()
                        .setEquipment(stepEquipmentDTO.getEquipment())
                        .setStep(stepEquipmentDTO.getStep())
                        .setQuantity(stepEquipmentDTO.getQuantity())
                        .setUnit(stepEquipmentDTO.getUnit());

                stepEquipments.add(stepEquipment);
                stepEquipmentRepository.save(stepEquipment);
            }
            step.setEquipmentSet(stepEquipments);

            stepRepository.save(step);

        }
        drink.setSteps(steps);

        return drinkRepository.save(drink) == drink;
    }

    @Override
    public boolean deleteDrink(int id) {
        if (drinkRepository.findById(id).isPresent()) {
            Drink drink = drinkRepository.findById(id).get();
            imageService.deleteFileFromS3bucket(drink.getImage_path());
            drinkRepository.delete(drink);
            return true;
        }
        return false;
    }

    @Override
    public Set<Map<String, Object>> viewAllDrinks() throws IOException {
        List<Drink> drinks = drinkRepository.findAll();

        Set<Map<String, Object>> res = new HashSet<>();

        for (Drink drink: drinks){
            if (drink.isPublic() || drink.getCreatedBy().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                res.add(findDrinkById(drink.getId()));
            }
        }

        return res;

    }

    @Override
    public Set<Map<String, Object>> viewDrinksByUser() throws IOException {
        Set<Map<String, Object>> res = new HashSet<>();
        List<Drink> drinks = drinkRepository.findAll();

        for (Drink d : drinks) {
            if (d.getCreatedBy().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                res.add(findDrinkById(d.getId()));
            }
        }
        return res;
    }

    @Override
    public Map<String, Object> findDrinkById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        Optional<Drink> drinks = drinkRepository.findById(id);
        if (!drinks.isPresent()){
            return null;
        }
        DrinkDTO drinkDTO = DrinkDTOMapper.toDrinkDTO(drinks.get());
        ret.put("drink", drinkDTO);
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