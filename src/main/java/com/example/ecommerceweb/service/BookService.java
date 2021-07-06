package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
   // public List<Book> getAllBooks();
    public Book saveABook(Long categoryId, Book book);
    public Category createACategory(Category category);
}
