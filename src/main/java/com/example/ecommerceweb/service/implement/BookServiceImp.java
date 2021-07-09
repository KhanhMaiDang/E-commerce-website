package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.repository.BookCategoryRepository;
import com.example.ecommerceweb.repository.BookRepository;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCategoryRepository categoryRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveABook(Long categoryId, Book book) {
        return categoryRepository.findById(categoryId).map(category -> {book.setCategory(category);
        return bookRepository.save(book);}).orElseThrow(()-> new CategoryNotFoundException(categoryId));
    }

    @Override
    public Category createACategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
