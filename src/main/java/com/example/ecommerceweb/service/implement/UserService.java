package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println(username);
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user==null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(user);
    }
}
