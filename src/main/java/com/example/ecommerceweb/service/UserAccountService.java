package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.User;

public interface UserAccountService {
    public User getUserByUsername(String username);
}
