package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.exception.BookException;
import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/bookstore")
public class CategoryController {
    @Autowired
    private BookService bookService;

    @PostMapping("/admin/category")
    public Category createACategory(@RequestBody Category category){
        return bookService.createACategory(category);
    }

    @GetMapping({"/public/categories","admin/categories"})
    public List<Category> getAllCategories(){
        return bookService.getAllCategories();
    }

    @GetMapping("/public/categories/{id}")
    public Category getCategoryById(@PathVariable Long id){
        Category category = bookService.getCategoryById(id);
        if(category!=null)
            return category;
        else
            throw new CategoryNotFoundException(id);
    }

    @PutMapping("/admin/categories/{id}")
    public Category updateACategory(@Valid @PathVariable(value = "id") Long id, @RequestBody Category category) throws ParseException {
        return bookService.updateACategory(id,category);
    }

    @DeleteMapping("/admin/categories/{id}")
    public String deleteACategory(@Valid @PathVariable(value = "id") Long id){
        if(bookService.deleteCategoryById(id))
            return "Delete succesfully";
        else
            return "Can not delete";
    }
}
