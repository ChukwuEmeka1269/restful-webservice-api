package com.restwithsergey.sergeyrest.service.implementation;

import com.restwithsergey.sergeyrest.Model.UserModel;
import com.restwithsergey.sergeyrest.configuration.util.RandomGenerator;
import com.restwithsergey.sergeyrest.dto.UserDto;
import com.restwithsergey.sergeyrest.repository.UserRepository;
import com.restwithsergey.sergeyrest.service.UserService;

import org.springframework.beans.BeanUtils;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    private final RandomGenerator generator;

    public UserServiceImpl(PasswordEncoder encoder, UserRepository userRepository, RandomGenerator generator) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.generator = generator;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("User already exist.");
        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);


        String generatedUserId = generator.generateUserId(20);
        userModel.setUserId(generatedUserId);

        userModel.setEncryptedPassword(encoder.encode(userDto.getPassword()));

        UserModel savedUser = userRepository.save(userModel);

        UserDto returnUserDto = new UserDto();

        BeanUtils.copyProperties(savedUser, returnUserDto);


        return returnUserDto;
    }

    @Override
    public UserDto getUser(String email) {

        UserModel storedUser = userRepository.findByEmail(email);
        if(storedUser == null) throw new UsernameNotFoundException(email);
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(storedUser, userDto);
        return userDto;

    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserDto userDto = new UserDto();
        UserModel userModel = userRepository.findByUserId(id);

        if(userModel == null) throw new UsernameNotFoundException("User not found with id "+ id);

        BeanUtils.copyProperties(userModel, userDto);

        return userDto;
    }
}
