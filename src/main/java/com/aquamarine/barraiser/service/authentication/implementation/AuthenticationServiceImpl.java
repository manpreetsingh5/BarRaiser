package com.aquamarine.barraiser.service.authentication.implementation;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.PasswordResetToken;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.network.response.JWTAuthenticationResponse;
import com.aquamarine.barraiser.repository.PasswordTokenRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.security.JWTTokenProvider;
import com.aquamarine.barraiser.service.authentication.interfaces.AuthenticationService;
import com.aquamarine.barraiser.service.email.interfaces.EmailService;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity requestResetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            emailService.createPasswordResetTokenForUser(user, token);
            SimpleMailMessage message = emailService.constructResetTokenEmail(token, user);
            emailService.sendEmail(message);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean validatePasswordToken(int user_id, String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != user_id)) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return false;
        }

        User user = passToken.getUser();
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                user, null, Arrays.asList(
//                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        return true;
    }

    @Override
    public void resetPassword(int user_id, String token) {
//        boolean result = validatePasswordToken(user_id, token);
//        if (result) {
//            model.addAttribute("message",
//                    messages.getMessage("auth.message." + result, null, locale));
//            return "redirect:/login?lang=" + locale.getLanguage();
//        }
//        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }


}
