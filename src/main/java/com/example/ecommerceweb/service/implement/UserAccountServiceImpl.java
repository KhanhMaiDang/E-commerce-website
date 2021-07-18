package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Role;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.RoleRepository;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllAccounts(){
        return userRepository.findAll();
    }

    public User getAccountById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    @Transactional
    public Set<User> getAllCustomer(){
        Role userRole = roleRepository.findRoleByName("USER");
        return userRole.getUsers();
    }
}
