package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/categories/{categoryId}/books")
    public Book createBook(@PathVariable(value="categoryId") Long categoryId, @Valid @RequestBody Book book){

        return bookService.saveABook(categoryId, book);
    }

    @GetMapping("/books")
    @ResponseBody
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BookDTO convertToDto(Book book){
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        bookDTO.setCategory(book.getCategory().getName());
        return bookDTO;
    }
}
