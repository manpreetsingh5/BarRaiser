package com.aquamarine.barraiser.service.authentication.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface AuthenticationService {
    ResponseEntity authenticateUserLogin(UserDTO userDTO);
    ResponseEntity registerUser(UserDTO userDTO);
    ResponseEntity requestResetPassword(String email) throws MessagingException;
    boolean validatePasswordToken(int user_id, String token);
    boolean resetPassword(int user_id, String oldPassword, String newPassword);
}
