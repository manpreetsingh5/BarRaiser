package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody String addNewUser (@RequestBody Person person) {

        UserDTO userDTO = UserDTO.builder().first_name(person.first_name).last_name(person.last_name)
                .email(person.email).password(person.password).status(person.status).build();

        userService.signUp(userDTO);

        return "Success\n";
    }

    @AllArgsConstructor
    static class Person {
        private String first_name;
        private String last_name;
        private String email;
        private String password;
        private boolean status;

    }

    @RequestMapping(path="/getUser/{player_id}", method= RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody User getUser(@PathVariable int player_id) {
        return userService.findUserById(player_id);
    }

    @RequestMapping(path="/allUsers", method= RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody Iterable<UserDTO> getAllUsers() {

        return userService.findAll();
    }

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> verifyUserCredentials(@RequestBody Login login){
        System.out.println(1);
        int id = userService.verify(login.email, login.password);

        if (id == -1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }
    }

    @AllArgsConstructor
    static class Login {
        private String email;
        private String password;
    }
}