package com.aquamarine.barraiser.service.user.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;

import java.util.List;

public interface UserService {
    void signUp(UserDTO userdto);
    UserDTO findUserByEmail(String email);
    List<UserDTO> findAll();
    int verify(String email, String password);
}