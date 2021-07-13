package com.example.ecommerceweb.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;


}
