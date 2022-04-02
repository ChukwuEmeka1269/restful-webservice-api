package com.restwithsergey.sergeyrest.service.implementation;

import com.restwithsergey.sergeyrest.Model.UserModel;
import com.restwithsergey.sergeyrest.configuration.util.RandomGenerator;
import com.restwithsergey.sergeyrest.dto.UserDto;
import com.restwithsergey.sergeyrest.exception.ErrorMessages;
import com.restwithsergey.sergeyrest.exception.UserServiceException;
import com.restwithsergey.sergeyrest.repository.UserRepository;
import com.restwithsergey.sergeyrest.service.UserService;

import org.modelmapper.ModelMapper;
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
        if(userRepository.findByEmail(userDto.getEmail()) != null) throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());
        if(userDto.getFirstname().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
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

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserModel existingUser = userRepository.findByUserId(userId);
        if(existingUser == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        if(existingUser.getEmail().equals(userDto.getEmail())
                && existingUser.getFirstname().equals(userDto.getFirstname())
                && existingUser.getLastname().equals(userDto.getLastname())) throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());

        existingUser.setEmail(userDto.getEmail());
        existingUser.setFirstname(userDto.getFirstname());
        existingUser.setLastname(userDto.getLastname());

        userRepository.save(existingUser);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(existingUser, UserDto.class);
    }

    @Override
    public UserDto updateEmail(String userId, String newEmail) {
        UserModel storedUser = userRepository.findByUserId(userId);
        if(storedUser == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        if(storedUser.getEmail().equals(newEmail)) throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());

        storedUser.setEmail(newEmail);

        userRepository.save(storedUser);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(storedUser, userDto);

        return userDto;
    }

    @Override
    public String updatePassword(String email, String newPassword) {
        UserModel storedUser = userRepository.findByEmail(email);
        if(storedUser == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if(storedUser.getEncryptedPassword().equals(encoder.encode(newPassword))) throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());

        storedUser.setEncryptedPassword(encoder.encode(newPassword));

        userRepository.save(storedUser);

        return "Password update successful";

    }


}
