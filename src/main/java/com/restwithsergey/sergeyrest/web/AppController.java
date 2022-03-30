package com.restwithsergey.sergeyrest.web;

import com.restwithsergey.sergeyrest.Model.UserModel;
import com.restwithsergey.sergeyrest.dto.UserDto;
import com.restwithsergey.sergeyrest.dto.UserRequestDto;
import com.restwithsergey.sergeyrest.dto.UserResponseDto;
import com.restwithsergey.sergeyrest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class AppController {

    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){

        UserResponseDto userResponseDto = new UserResponseDto();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequestDto, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, userResponseDto);

        return userResponseDto;
    }

    @GetMapping("/get")
    public UserModel getUser(String email){
        UserDto userDto = userService.getUser(email);

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        return userModel;
    }


//    @GetMapping("/get")
//    public String getUser(){
//       return "Get user was called";
//    }



    @PutMapping("/update")
    public String updateUser(){
        return "User updated.";
    }

    @DeleteMapping("/delete")
    public String deleteUser(){
        return "User deleted";
    }
}
