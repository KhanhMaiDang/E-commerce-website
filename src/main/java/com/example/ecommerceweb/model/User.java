package com.example.ecommerceweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String name;
    private String phoneNumber;
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> myRatings;

    public void addRole(Role role) {
        roles.add(role);
    }
    public User(String username, String name, String phoneNumber, String password, String email) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }

    public User(){

    }

    public void deleteAllRoles(){
       // System.out.println(roles);
//        for (Role r: roles){
//            roles.remove(r);
//        }
        roles=null;
    }

}
