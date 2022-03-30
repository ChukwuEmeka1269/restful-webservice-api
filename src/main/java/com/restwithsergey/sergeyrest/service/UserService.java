package com.restwithsergey.sergeyrest.service;

import com.restwithsergey.sergeyrest.dto.UserDto;




public interface UserService{
    UserDto createUser(UserDto userDto);

    UserDto getUser(String email);


}
