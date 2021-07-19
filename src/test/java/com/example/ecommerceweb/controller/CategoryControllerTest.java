package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController = new CategoryController();

    @Mock
    BookService bookService;
    @Test
    void createACategoryTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.createACategory(any(Category.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Category category = new Category("Comic");
        Category createdCat = categoryController.createACategory(category);

        assertEquals(category, createdCat);
    }

    @Test
    void getAllCategoriesTest(){
        List<Category> categoryList = new ArrayList<Category>();
        Category cat1 = new Category("Comic");
        Category cat2 = new Category("Book");

        categoryList.add(cat1);
        categoryList.add(cat2);

        when(bookService.getAllCategories()).thenReturn(categoryList);

        List<Category> categories = categoryController.getAllCategories();

        assertEquals(2, categories.size());
        assertThat(categories.get(0).getName()).isEqualTo("Comic");
    }

    @Test
    void deleteACategory() {
        when(bookService.deleteCategoryById(any(Long.class))).thenReturn(true);

        String result = categoryController.deleteACategory(1L);

        assertEquals("Delete successfully",result);
    }
}