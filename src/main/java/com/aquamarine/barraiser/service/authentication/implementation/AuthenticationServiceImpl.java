package com.aquamarine.barraiser.service.authentication.implementation;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.PasswordResetToken;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.PasswordTokenRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.security.JWTTokenProvider;
import com.aquamarine.barraiser.service.authentication.interfaces.AuthenticationService;
import com.aquamarine.barraiser.service.email.interfaces.EmailService;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URI;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    JWTTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity authenticateUserLogin(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserDTO res = userService.findUserByEmail(userDTO.getEmail());
        res.setAccessToken(jwt);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity registerUser(UserDTO userDTO) {
        if(userRepository.existsUserByEmail(userDTO.getEmail())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User user = new User(userDTO.getEmail(), userDTO.getFirst_name(),
                userDTO.getLast_name(),userDTO.getPassword(), userDTO.getStatus());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body("User registered successfully");
    }

    @Override
    public ResponseEntity requestResetPassword(String email) throws MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(email);


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            emailService.createPasswordResetTokenForUser(user, token);
            MimeMessage message = emailService.constructResetTokenEmail(token, user);
            emailService.sendEmail(message);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean validatePasswordToken(int user_id, String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        System.out.println(passToken.getExpiryDate());
        if ((passToken == null) || (passToken.getUser().getId() != user_id)) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        if (passToken.getExpiryDate().after(new Date(System.currentTimeMillis()))) {
            User user = userRepository.findById(user_id).get();
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return true;
        }

        return false;
    }

    @Override
    public boolean resetPassword(int user_id, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                System.out.println(newPassword);
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }

        return false;
    }


}
