package com.example.ecommerceweb.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String username;
        private String name;
        private Long phoneNumber;
        private List<String> roles;

        public JwtResponse(String accessToken, Long id, String username, String name, Long phoneNumber, List<String> roles) {
            this.token = accessToken;
            this.id = id;
            this.username = username;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.roles = roles;
        }

    }
