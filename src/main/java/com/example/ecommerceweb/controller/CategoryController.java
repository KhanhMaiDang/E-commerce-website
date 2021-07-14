package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/bookstore")
public class CategoryController {
    @Autowired
    private BookService bookService;

    @PostMapping("/public/categories")
    public Category createACategory(@RequestBody Category category){
        return bookService.createACategory(category);
    }
}
