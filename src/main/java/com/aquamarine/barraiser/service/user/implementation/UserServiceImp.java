package com.aquamarine.barraiser.service.user.implementation;

import com.aquamarine.barraiser.dto.mapper.UserDTOMapper;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    UserDTOMapper userDtOMapper = new UserDTOMapper();



    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return UserDTOMapper.toUserDTO(user);

    }

    @Override
    public List<UserDTO> findAll(){
        List<User> arrayList = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();

        for (User u: arrayList){
            UserDTO userDTO = userDtOMapper.toUserDTO(u);
            dtoList.add(userDTO);
        }

        return dtoList;

    }

}