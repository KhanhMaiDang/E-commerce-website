package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface UserAccountService {
    public User getUserByUsername(String username);
    public List<User> getAllAccounts();
    public User getAccountById(Long id);
    public Set<User> getAllCustomer();
    public boolean deleteAnAccount(Long accId);
}
