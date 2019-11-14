package com.aquamarine.barraiser.service.authentication.implementation;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.network.response.JWTAuthenticationResponse;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.security.JWTTokenProvider;
import com.aquamarine.barraiser.service.authentication.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity authenticateUserLogin(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthenticationResponse(jwt));
    }

    @Override
    public ResponseEntity registerUser(UserDTO userDTO) {
        if(userRepository.existsUserByEmail(userDTO.getEmail())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        // Creating user's account
        System.out.println(userDTO.getPassword());
        User user = new User(userDTO.getEmail(), userDTO.getFirst_name(),
                userDTO.getLast_name(),userDTO.getPassword(), userDTO.getStatus());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body("User registered successfully");
    }
}
