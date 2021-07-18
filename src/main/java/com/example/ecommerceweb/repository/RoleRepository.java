package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Role;
import com.example.ecommerceweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
