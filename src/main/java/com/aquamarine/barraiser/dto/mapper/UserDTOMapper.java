package com.aquamarine.barraiser.dto.mapper;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;

public class UserDTOMapper {
    public static UserDTO toUserDTO(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setFirst_name(user.getFirst_name())
                .setLast_name(user.getLast_name())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setStatus(user.isStatus());
    }
}