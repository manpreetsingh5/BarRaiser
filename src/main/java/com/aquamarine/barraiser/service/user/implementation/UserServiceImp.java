package com.aquamarine.barraiser.service.user.implementation;

import com.aquamarine.barraiser.dto.mapper.UserDTOMapper;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;


    UserDTOMapper userDtOMapper = new UserDTOMapper();

    @Override
    public void signUp(UserDTO userdto) {

//        final String hashed = Hashing.sha256()
//                .hashString(userdto.getPassword(), StandardCharsets.UTF_8)
//                .toString();

//        User user = User.builder().email(userdto.getEmail()).first_name(userdto.getFirst_name())
//                .last_name(userdto.getLast_name())
//                .hash_pass(hashed).build();
//
//        userRepository.save(user);

    }


    @Override
    public UserDTO findUserByEmail(String email) {
//        User user = userRepository.findByEmail(email);
//        UserDTO userDTO = userDtOMapper.toGameDTO(user);

        return null;
    }

    @Override
    public User findUserById(int id){

        User user = userRepository.findById(id).get();
        UserDTO userDTO = userDtOMapper.toUserDTO(user);

        return user;
    }

    @Override
    public List<UserDTO> findAll(){
//        List<User> arrayList = userRepository.findAll();
//        List<UserDTO> dtoList = new ArrayList<>();
//
//        for (User u: arrayList){
//            UserDTO userDTO = userDtOMapper.toGameDTO(u);
//            dtoList.add(userDTO);
//        }

        return null;

    }

    @Override
    public int verify(String email, String password) {

//        final String service_password = Hashing.sha256()
//                .hashString(password, StandardCharsets.UTF_8)
//                .toString();
//
//        int areCredentialsValid = -1;
//
//        User u = userRepository.findByEmail(email);
//        System.out.println(service_password);
//        System.out.println(u.getHash_pass());
//        System.out.println(service_password.equals(u.getHash_pass()));
//
//        if (u != null && (service_password.equals(u.getHash_pass()))){
//            System.out.println(u.getId());
//            return u.getId();
//
//        }
//        System.out.println("test");
        return 0;

    }

}