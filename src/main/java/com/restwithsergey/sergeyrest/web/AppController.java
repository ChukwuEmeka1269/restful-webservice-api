package com.restwithsergey.sergeyrest.web;

import com.restwithsergey.sergeyrest.dto.*;


import com.restwithsergey.sergeyrest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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



    @PutMapping(path = "/update/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
    public UserResponseDto updateUser(@PathVariable String userId, @RequestBody UserRequestDto userRequestDto){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequestDto, userDto);

        UserDto createdUser = userService.updateUser(userId, userDto);

        UserResponseDto userResponseDto = new UserResponseDto();

        BeanUtils.copyProperties(createdUser, userResponseDto);

        return userResponseDto;
    }

    @PutMapping( path = "/updateEmail/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
    public UserResponseDto updateUserEmail(@PathVariable String userId, @RequestBody UpdateRequest request){

        UserDto updatedUser = userService.updateEmail(userId, request.getNewEmail());

        UserResponseDto userResponseDto = new UserResponseDto();

        BeanUtils.copyProperties(updatedUser, userResponseDto);

        return userResponseDto;
    }

    @PutMapping( path = "/updatePassword/{email}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE} )
    public String updateUserPassword(@PathVariable String email, @RequestBody UpdateRequest request){
        return userService.updatePassword(email, request.getNewPassword());
    }



    @DeleteMapping(path = "/delete/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userId){
        OperationStatusModel operationStatus = new OperationStatusModel();

        operationStatus.setOperationName(OperationName.DELETE.name());
        userService.deleteUser(userId);
        operationStatus.setOperationStatus(OperationStatus.SUCCESS.name());
        return operationStatus;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserResponseDto> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "25") int limit){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto: users){
            UserResponseDto userResponseDto = new UserResponseDto();
            BeanUtils.copyProperties(userDto, userResponseDto);
            responseDtos.add(userResponseDto);
        }

        return responseDtos;
    }
}
