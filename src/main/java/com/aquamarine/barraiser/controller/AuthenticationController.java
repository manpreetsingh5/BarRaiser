package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.ApiController;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.authentication.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends ApiController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        return authenticationService.authenticateUserLogin(userDTO);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return authenticationService.registerUser(userDTO);
    }

    @RequestMapping(value = "/requestPasswordReset", method = RequestMethod.POST)
    @ResponseBody
    public void requestPasswordReset(@RequestParam("email") String userEmail) {
        authenticationService.requestResetPassword(userEmail);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public void resetPassword(@RequestParam("id") int user_id, @RequestParam("token") String token) {

    }
}
