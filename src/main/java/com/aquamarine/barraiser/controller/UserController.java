package com.aquamarine.barraiser.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.List;

@RestController
@RequestMapping(path="/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AmazonS3 s3client;

    @Value("${app.awsServices.endpoint}")
    private String endpointUrl;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @RequestMapping(path="/getUser", method= RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody UserDTO getUser(@RequestParam int player_id) {
        System.out.println(player_id);
        return userService.findById(player_id);

    }

    @RequestMapping(path="/allUsers", method= RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(path = "/getUserByEmail", method = RequestMethod.GET)
    public @ResponseBody UserDTO getUserByID(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @RequestMapping(path="/uploadImage", method = RequestMethod.POST)
    public void uploadFile(@RequestPart(value = "file") MultipartFile multipartFile){
        String fileUrl = "";
        String  status = null;

        try {

            //converting multipart file to file
            File file = imageService.convertMultiPartToFile(multipartFile, "test");

            //filename
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            System.out.println(fileUrl);

//            imageService.uploadFileToS3bucket(fileName, file, "/drinks");

            file.delete();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    @RequestMapping(path="/getImage", method = RequestMethod.POST)
    public void getFile(@RequestPart(value = "file") MultipartFile multipartFile){
        String fileUrl = "";
        String  status = null;

        try {

            //converting multipart file to file
            File file = imageService.convertMultiPartToFile(multipartFile, "test");

            //filename
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);


//            imageService.uploadFileToS3bucket(fileName, file, "/images/drinks");

            file.delete();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}