package com.aquamarine.barraiser.controller;

import com.aquamarine.barraiser.ApiController;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.network.response.JWTAuthenticationResponse;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends ApiController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ResponseEntity<?> authenticateUser() {
        ;

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userdto.getEmail(),
//                        userdto.getPassword()
//                )
//        );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "test10@gmail.com",
                        "password"
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ResponseEntity<?> registerUser() {
//        if(userRepository.existsUserByEmail(userDTO.getEmail())) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//
//        // Creating user's account
//        User user = new User(userDTO.getEmail(), userDTO.getFirst_name(),
//                userDTO.getLast_name(), userDTO.getStatus());
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user = new User("test10@gmail.com", "Manny", "Singh", "TRAINEE");
        user.setPassword(passwordEncoder.encode("password"));


        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body("User registered successfully");
    }
}
