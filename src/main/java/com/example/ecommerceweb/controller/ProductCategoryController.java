package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookstore")
public class ProductCategoryController {

    @Autowired
    private BookService bookService;

//    @GetMapping("/public/categories/{catId}/books")
//    public List<BookDTO> getBooksByCategory(@Valid @PathVariable(value = "catId") Long id){
//        List<Book> books = bookService.getBooksByCategory(catId);
//        return books.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
}
