package com.restwithsergey.sergeyrest.service;

import com.restwithsergey.sergeyrest.Model.UserModel;
import com.restwithsergey.sergeyrest.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    public final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userByEmail = userRepository.findByEmail(email);
        if(userByEmail == null){
            throw new UsernameNotFoundException(email + "not found.");
        }
        return new User(userByEmail.getEmail(), userByEmail.getEncryptedPassword(), new ArrayList<>());
    }
}
