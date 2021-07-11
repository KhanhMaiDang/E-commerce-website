package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
   // public List<Book> getAllBooks();
    public Book saveABook(Long categoryId, Book book);
    public Book saveABook(Book book);
    public Category createACategory(Category category);
    public List<Book> getAllBooks();
    public Category getCategoryById(Long id);
    public Book getBookById(Long id);
    public Book getBookByName(String name);
    public Book getBookByAuthor(String author);
    //public Book getBookByCategory();
    public Category getCategoryByName(String name); // return null if the category not found
    public Book updateABook(Long id, Book book);
    public boolean deleteBookById(Long id);


}
