package com.restwithsergey.sergeyrest.web;

import com.restwithsergey.sergeyrest.dto.UserDto;
import com.restwithsergey.sergeyrest.dto.UserRequestDto;
import com.restwithsergey.sergeyrest.dto.UserResponseDto;
import com.restwithsergey.sergeyrest.exception.ErrorMessages;
import com.restwithsergey.sergeyrest.exception.UserServiceException;
import com.restwithsergey.sergeyrest.service.UserService;
import org.modelmapper.ModelMapper;
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

    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResponseDto registerUser(@RequestBody UserRequestDto userRequestDto){

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequestDto, userDto);

        if(userRequestDto.getFirstname().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

//        if(userRequestDto.getEmail() != null) throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());

        UserDto createdUser = userService.createUser(userDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        BeanUtils.copyProperties(createdUser, userResponseDto);

        return userResponseDto;
    }

    @GetMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResponseDto getUser(@PathVariable String id){
        UserDto userDto = userService.getUserByUserId(id);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(userDto, UserResponseDto.class);
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
