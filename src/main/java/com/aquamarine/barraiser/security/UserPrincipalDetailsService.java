package com.aquamarine.barraiser.security;

import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).get();
        UserPrincipal userPrincipal;
        userPrincipal = UserPrincipal.create(user);

        return userPrincipal;
    }

    @Transactional
    public UserDetails loadUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
