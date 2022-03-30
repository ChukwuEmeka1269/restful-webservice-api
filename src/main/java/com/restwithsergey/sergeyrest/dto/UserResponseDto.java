package com.restwithsergey.sergeyrest.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;

}
