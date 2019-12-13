package com.aquamarine.barraiser.service.authentication.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.network.response.JWTAuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity authenticateUserLogin(UserDTO userDTO);
    ResponseEntity registerUser(UserDTO userDTO);
    ResponseEntity requestResetPassword(String email);
    boolean validatePasswordToken(int user_id, String token);
    void resetPassword(int user_id, String token);
}
