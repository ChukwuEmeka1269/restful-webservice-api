package com.restwithsergey.sergeyrest.service;

import com.restwithsergey.sergeyrest.dto.UserDto;

import java.util.List;


public interface UserService{
    UserDto createUser(UserDto userDto);

    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    UserDto updateEmail(String userId, String newEmail);

    String updatePassword(String email, String newPassword);

    void deleteUser(String userId);

    List<UserDto> getUsers(int page, int limit);
//
//    String updateFirstname(String email, String oldFirstname, String newFirstname);
//
//    String updateLastname(String email, String oldLastname, String newLastname);


}
