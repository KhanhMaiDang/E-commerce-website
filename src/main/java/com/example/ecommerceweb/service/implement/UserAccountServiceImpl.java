package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    UserRepository userRepository;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
