package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.exception.BookException;
import com.example.ecommerceweb.exception.CanNotUploadImageException;
import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.model.Rating;
import com.example.ecommerceweb.payload.response.MessageResponse;
import com.example.ecommerceweb.service.BookService;
import com.example.ecommerceweb.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookstore")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/admin/hello")
    //@PreAuthorize("hasAuthority('USER')")
    //@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String helloWorld(){
        return "Hello world";
    }

    @PostMapping("/admin/{bookId}/upImage")
    public ResponseEntity<String> uploadImage( @Valid @PathVariable(value = "bookId") Long id, @RequestParam("file")MultipartFile file){
        try{
            byte[] image = file.getBytes();
            Book book = bookService.getBookById(id);
            book.setImage(image);
            bookService.updateABook(id,book);

        }
        catch (Exception e){
            log.error("ERROR",e);
            throw new CanNotUploadImageException();
        }
        return new ResponseEntity<>("Upload successfully!", HttpStatus.OK);
    }

    @GetMapping("/public/{bookId}/image")
    public String getImages(@PathVariable(value = "bookId") Long id, Model model) {
        try {
            log.info("Id= " + id);
            Book book = bookService.getBookById(id);
            model.addAttribute("id", book.getId());
            model.addAttribute("name", book.getName());
            byte[] encode = java.util.Base64.getEncoder().encode(book.getImage());
//            System.out.println("encode"+encode);
          String encodeString = Base64.getEncoder().encodeToString(book.getImage());
//            System.out.println("STring "+encodeString);
            model.addAttribute("image", encodeString);
            return "imageDetails";
        } catch (Exception e) {
            log.error("Error", e);
            model.addAttribute("message", "Error in getting image");
            return "redirect:/";
        }
    }


    @PostMapping("/admin/book")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDto) throws ParseException {
        Book book = this.convertToEntity(bookDto);
        Category category = bookService.getCategoryByName(bookDto.getCategory());
        if(category!=null)
             book.setCategory(bookService.getCategoryByName(bookDto.getCategory()));
        else
            //throw new CategoryNotFoundException(bookDto.getCategory());
            return ResponseEntity.internalServerError().body(new MessageResponse("Category not found"));
        return ResponseEntity.ok(convertToDto(bookService.saveABook(book)));
    }

    @PostMapping("/admin/categories/{catId}/book")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookDTO createBookInACategory(@Valid @RequestBody BookDTO bookDto, @PathVariable(value = "catId") Long id) throws ParseException {
        Book book = this.convertToEntity(bookDto);
        Category category = bookService.getCategoryById(id);
        if(category!=null)
            book.setCategory(category);
        else
            throw new CategoryNotFoundException(bookDto.getCategory());
        return convertToDto(bookService.saveABook(id,book));
    }

    @GetMapping("/public/books")
    @ResponseBody
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/public/books/{id}")
    public BookDTO getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        System.out.println("BC "+book);
        if(book!=null)
            return convertToDto(book);
        else
            throw new BookException(id);
    }

    @GetMapping("/public/categories/{catId}/books")
    public List<BookDTO> getBooksByCategory(@Valid @PathVariable(value = "catId") Long id){
        List<Book> books = bookService.getBooksByCategory(id);
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PutMapping("/admin/books/{id}/edit")
    public ResponseEntity<?>  updateABook(@Valid @PathVariable(value = "id") Long id, @RequestBody BookDTO bookDto) throws ParseException {
        Category category = bookService.getCategoryByName(bookDto.getCategory());
        System.out.println(category);
        if(category!=null) {
            Book book = this.convertToEntity(bookDto);
            book.setCategory(bookService.getCategoryByName(bookDto.getCategory()));
            return ResponseEntity.ok(convertToDto(bookService.updateABook(id,book)));
        }
        else {
            return ResponseEntity.internalServerError().body(new MessageResponse("Category not found"));
        }
    }

    @DeleteMapping("/admin/books/{id}")
    public String deleteABook(@Valid @PathVariable(value = "id") Long id){
       if(bookService.deleteBookById(id))
           return "Delete succesfully";
       else
           return "Can not delete";
    }

    @GetMapping("/public/books/featured")
    public List<BookDTO> getFeaturedBooks(){
        List<Book> books = bookService.getFeaturedBooks();
            return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BookDTO convertToDto(Book book){
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        bookDTO.setCategory(book.getCategory().getName());

        if(book.getImage()!=null) {
            String encodeString = Base64.getEncoder().encodeToString(book.getImage());
            bookDTO.setImage(encodeString);
        }
        List<Rating> ratings = bookService.getAllRatingsOfABook(book.getId());
        if(ratings !=null && ratings.size() != 0) {
            Integer numRev = ratings.size();
            bookDTO.setNumReviews(numRev);
            log.info("book id" + book.getId() + "numRev" + numRev);
            System.out.println(ratings);
            bookDTO.setAvgRating(ratingService.calcAvgRating(book.getId()));
        }
        else{
            bookDTO.setNumReviews(0);
            bookDTO.setAvgRating(0F);
        }
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) throws ParseException {
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }
}
