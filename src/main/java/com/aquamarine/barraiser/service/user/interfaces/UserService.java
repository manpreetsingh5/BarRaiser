package com.aquamarine.barraiser.service.user.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    void signUp(UserDTO userdto);
    UserDTO findUserByEmail(String email);
    UserDTO findUserById(int id);
    List<UserDTO> findAll();
    int verify(String email, String password);
}