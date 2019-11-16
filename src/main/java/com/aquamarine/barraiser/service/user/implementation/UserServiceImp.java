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
    public void signUp(UserDTO userdto) {

        User user = User.builder().email(userdto.getEmail()).first_name(userdto.getFirst_name())
                .last_name(userdto.getLast_name())
                .password(userdto.getPassword()).build();

        userRepository.save(user);
    }


    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
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

    @Override
    public int verify(String email, String password) {

        int areCredentialsValid = -1;

        User u = userRepository.findByEmail(email);

        if (u != null && (password.equals(u.getPassword()))){
            return u.getId();

        }
        return areCredentialsValid;
    }

}