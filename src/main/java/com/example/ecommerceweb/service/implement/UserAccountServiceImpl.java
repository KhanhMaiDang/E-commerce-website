package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.exception.UserAccountNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.Role;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.RoleRepository;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
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

    @Override
    public boolean deleteAnAccount(Long accId) {
        Optional<User> tmpAcc = userRepository.findById(accId);
        if (tmpAcc.isPresent()) {
            log.info("Acc id " + accId);
            User account = tmpAcc.get();
            System.out.println(account);
            account.deleteAllRoles();
            userRepository.deleteById(accId);
            return true;
        } else
            return false;
    }

    @Override
    public User updateUserAccount(User newUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = ((CustomUserDetail)auth.getPrincipal());
        User currentUser = customUserDetail.getUser();

        return userRepository.findById(currentUser.getId()).map(user -> {
            user.setPassword(newUser.getPassword());
            user.setName(newUser.getName());
            user.setPhoneNumber(newUser.getPhoneNumber());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserAccountNotFoundException(currentUser.getId()));
    }
}
