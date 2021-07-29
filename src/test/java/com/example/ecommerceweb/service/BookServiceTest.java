package com.example.ecommerceweb.service;

import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.model.Rating;
import com.example.ecommerceweb.repository.BookCategoryRepository;
import com.example.ecommerceweb.repository.BookRepository;
import com.example.ecommerceweb.service.implement.BookServiceImp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

   @InjectMocks
    BookService bookService = new BookService() {

       @Override
       public List<Book> getFeaturedBooks() {
           return null;
       }

       @Override
       public Book saveABook(Long categoryId, Book book) {
           return null;
       }

       @Override
       public Book saveABook(Book book) {
           return null;
       }

       @Override
       public Category createACategory(Category category) {
           return categoryRepository.save(category);
       }

       @Override
       public List<Book> getAllBooks() {
           return null;
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
           return null;
       }

       @Override
       public Book getBookByName(String name) {
           return null;
       }

       @Override
       public Book getBookByAuthor(String author) {
           return null;
       }

       @Override
       public List<Book> getBooksByCategory(Long categoryId) {
           return null;
       }

       @Override
       public Category getCategoryByName(String name) {
           return null;
       }

       @Override
       public Book updateABook(Long id, Book book) {
           return null;
       }

       @Override
       public boolean deleteBookById(Long id) {
           return false;
       }

       @Override
       public List<Category> getAllCategories() {
           return categoryRepository.findAll();
       }

       @Override
       public Category updateACategory(Long id, Category newCategory) {
           return null;
       }

       @Override
       public boolean deleteCategoryById(Long id) {
           return false;
       }

       @Override
       public Book updateAvgRating(Long bookId, Float avgRating) {
           return null;
       }

       @Override
       public List<Rating> getAllRatingsOfABook(Long bookId) {
           return null;
       }
   };

   @Mock
    BookCategoryRepository categoryRepository;

    @Test
    public void getAllCategoryTest(){
        List<Category> categoryList = new ArrayList<Category>();
        Category cat1 = new Category("Comic");
        Category cat2 = new Category("Book");

        categoryList.add(cat1);
        categoryList.add(cat2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> catList = bookService.getAllCategories();

        assertEquals(2, categoryList.size());
        verify(categoryRepository, times(1)).findAll();

    }

    @Test
    public void getCategoryByIdTest(){
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(new Category("Comic")));

        Category category = bookService.getCategoryById(1L);

        assertEquals("Comic",category.getName());
    }

    @Test
    public void createCategoryTest(){
        Category category = new Category("Comic");

        bookService.createACategory(category);

        verify(categoryRepository, times(1)).save(category);
    }

}