package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.DTO.UserDTO;
import com.example.ecommerceweb.exception.CanNotDeleteObjectException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.service.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookstore")
public class UserController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("admin/accounts")
    public List<User> getAllAccounts(){
        List<User> userList = userAccountService.getAllAccounts();
        //return userList.stream().map(this::convertToDto).collect(Collectors.toList());
        return  userList;
    }

    @GetMapping("admin/customers")
    public List<UserDTO> getAllCustomers(){
        Set<User> userList = userAccountService.getAllCustomer();
        return userList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/accounts/{id}")
    public UserDTO getAccountById(@Valid @PathVariable(value = "id")Long id){
        return convertToDto(userAccountService.getAccountById(id));
    }

    @DeleteMapping("/admin/accounts/{accountId}/delete")
    public ResponseEntity<String> deleteAnAccount(@Valid @PathVariable(value = "accountId") Long id){
        if(!userAccountService.deleteAnAccount(id)){
            throw new CanNotDeleteObjectException();
        }
        return ResponseEntity.ok("Delete successfully!");
    }

    private UserDTO convertToDto(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) throws ParseException {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
