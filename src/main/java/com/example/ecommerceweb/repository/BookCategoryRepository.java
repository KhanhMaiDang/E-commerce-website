package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<Category, Long> {
    public Category getCategoryByName(String name);
}
