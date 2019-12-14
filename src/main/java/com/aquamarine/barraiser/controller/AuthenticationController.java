package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.ApiController;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.authentication.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

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

    @RequestMapping(value = "/requestPasswordReset", method = RequestMethod.GET)
    @ResponseBody
    public void requestPasswordReset(@RequestParam("email") String userEmail) throws MessagingException {
        authenticationService.requestResetPassword(userEmail);
    }

    @RequestMapping(value = "/validateToken", method = RequestMethod.GET)
    public ResponseEntity<?> validateToken(@RequestParam("id") int user_id, @RequestParam("token") String token) {
        if (authenticationService.validatePasswordToken(user_id, token)) {
            Collection<? extends GrantedAuthority> set = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            return new ResponseEntity<>("Password token validated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Password token denied!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ResponseEntity<?> resetPassword(@RequestParam("id") int user_id, @RequestParam String new_password) {
        if (authenticationService.resetPassword(user_id, new_password)) {
            return new ResponseEntity<>("Password reset success!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Password reset failed!", HttpStatus.BAD_REQUEST);
    }
}
