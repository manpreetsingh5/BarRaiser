package com.aquamarine.barraiser.service.user.interfaces;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;

import java.util.List;

public interface UserService {
    UserDTO findById(int id);
    UserDTO findUserByEmail(String email);
    List<UserDTO> findAll();
}