package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.exception.BookException;
import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.model.Rating;
import com.example.ecommerceweb.repository.BookCategoryRepository;
import com.example.ecommerceweb.repository.BookRepository;
import com.example.ecommerceweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Book saveABook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Category createACategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            return category.get();
        }
        else
            return null;
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent())
            return book.get();
        else
            return null;
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    @Override
    public Book getBookByAuthor(String author) {
        return bookRepository.findBookByAuthor(author);
    }

//    @Override
//    public Book getBookByCategory() {
//        return null;
//    }


    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }

    @Override
    public Book updateABook(Long id, Book book) {
        return bookRepository.findById(id).map(book2 -> {
            book2.setCategory(book.getCategory());
            book2.setDescription(book.getDescription());
            book2.setName(book.getName());
            book2.setAuthor(book.getAuthor());
            book2.setPublisher(book.getPublisher());
            book2.setPrice(book.getPrice());
            book2.setRemaining(book.getRemaining());
            book2.setImageUrl(book.getImageUrl());
            return bookRepository.save(book2);
        }).orElseThrow(() -> new BookException(id));
    }

    @Override
    public boolean deleteBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            bookRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateACategory(Long id, Category newCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            category.setDescription(newCategory.getDescription());
            return categoryRepository.save(category);
        }).orElseThrow(()->new CategoryNotFoundException(id));
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Book> getBooksByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotFoundException(categoryId));
        return category.getBooks();
    }

    @Override
    public Book updateAvgRating(Long bookId, Float avgRating) {
        return bookRepository.findById(bookId).map(book -> {
            book.setAvgRating(avgRating);
            return bookRepository.save(book);
        }).orElseThrow(()->new BookException(bookId));
    }

    public List<Rating> getAllRatingsOfABook(Long bookId){
        Book book = getBookById(bookId);
        return book.getRatings();
    }
}
