package com.restwithsergey.sergeyrest.web;

import com.restwithsergey.sergeyrest.dto.UserDto;
import com.restwithsergey.sergeyrest.dto.UserRequestDto;
import com.restwithsergey.sergeyrest.dto.UserResponseDto;
import com.restwithsergey.sergeyrest.service.UserService;
import org.springframework.beans.BeanUtils;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class AppController {

    private final UserService userService;


    public AppController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        System.out.println("got here");
        UserResponseDto userResponseDto = new UserResponseDto();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequestDto, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, userResponseDto);

        return userResponseDto;
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResponseDto getUser(@PathVariable String id){
        UserDto userDto = userService.getUserByUserId(id);

        UserResponseDto userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(userDto, userResponseDto);

        return userResponseDto;
    }



    @PutMapping("/update")
    public String updateUser(){
        return "User updated.";
    }

    @DeleteMapping("/delete")
    public String deleteUser(){
        return "User deleted";
    }
}
