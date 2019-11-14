package com.aquamarine.barraiser.service.authentication.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.network.response.JWTAuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity authenticateUserLogin(UserDTO userDTO);
    ResponseEntity registerUser(UserDTO userDTO);
}
