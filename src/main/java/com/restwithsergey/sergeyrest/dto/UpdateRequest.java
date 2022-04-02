package com.restwithsergey.sergeyrest.dto;

import lombok.Data;

@Data
public class UpdateRequest {
    private String newEmail;
    private String newFirstname;
    private String newLastname;
    private String newPassword;
}
