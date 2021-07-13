package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);

}
