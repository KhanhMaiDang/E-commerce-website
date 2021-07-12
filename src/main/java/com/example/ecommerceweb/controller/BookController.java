package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.exception.BookException;
import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/admin/hello")
    //@PreAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String helloWorld(){
        return "Hello world";
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookDTO createBook(@Valid @RequestBody BookDTO bookDto) throws ParseException {
        Book book = this.convertToEntity(bookDto);
//        System.out.println(bookDto.getCategory());
//        System.out.println(bookService.getCategoryByName(bookDto.getCategory()));
        Category category = bookService.getCategoryByName(bookDto.getCategory());
        if(category!=null)
             book.setCategory(bookService.getCategoryByName(bookDto.getCategory()));
        else
            throw new CategoryNotFoundException(bookDto.getCategory());
        return convertToDto(bookService.saveABook(book));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/books")
    @ResponseBody
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    public BookDTO getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        if(book!=null)
            return convertToDto(book);
        else
            throw new BookException(id);
    }

    @PutMapping("/books/{id}")
    public Book updateABook(@Valid @PathVariable(value = "id") Long id, @RequestBody BookDTO bookDto) throws ParseException {
        Category category = bookService.getCategoryByName(bookDto.getCategory());
        System.out.println(category);
        if(category!=null) {
            Book book = this.convertToEntity(bookDto);
            book.setCategory(bookService.getCategoryByName(bookDto.getCategory()));
            return bookService.updateABook(id,book);
        }
        else {
            throw new CategoryNotFoundException(bookDto.getCategory());
        }
    }

    @DeleteMapping("books/{id}")
    public String deleteABook(@Valid @PathVariable(value = "id") Long id){
       if(bookService.deleteBookById(id))
           return "Delete succesfully";
       else
           return "Can not delete";
    }

    private BookDTO convertToDto(Book book){
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        bookDTO.setCategory(book.getCategory().getName());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) throws ParseException {
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }
}
