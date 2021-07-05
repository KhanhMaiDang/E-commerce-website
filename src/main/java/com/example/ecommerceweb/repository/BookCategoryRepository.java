package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<Category, Long> {
}
