package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookstore")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/categories/{categoryId}/books")
    public Book createBook(@PathVariable(value="categoryId") Long categoryId, @Valid @RequestBody Book book){

        return bookService.saveABook(categoryId, book);
    }
}
