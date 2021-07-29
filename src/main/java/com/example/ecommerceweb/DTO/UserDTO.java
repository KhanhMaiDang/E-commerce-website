package com.example.ecommerceweb.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String name;
//    private Long phoneNumber;
    String phoneNumber;
    private String email;

}
