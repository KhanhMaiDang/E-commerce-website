package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.DTO.BookDTO;
import com.example.ecommerceweb.DTO.RatingDTO;
import com.example.ecommerceweb.exception.CategoryNotFoundException;
import com.example.ecommerceweb.exception.NoMatchUserException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.example.ecommerceweb.model.Rating;
import com.example.ecommerceweb.service.BookService;
import com.example.ecommerceweb.service.RatingService;
import com.example.ecommerceweb.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
@Slf4j
public class RatingController {

    @Autowired
    RatingService ratingService;
    @Autowired
    BookService bookService;
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/user/books/{bookId}/rating")
    public RatingDTO ratesABook(@Valid @PathVariable(value = "bookId") Long id, @RequestBody Rating rating){
        log.error("rating"+id);
        return convertToDto(ratingService.addRating(id,rating));
    }

    @GetMapping("/public/books/{bookId}/ratings")
    public List<RatingDTO> getAllRatingOfABook(@Valid @PathVariable(value = "bookId") Long bookId){
        List<Rating> ratings = ratingService.getAllRatingsOfABook(bookId);
        return ratings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/public/ratings")
    public List<RatingDTO> getAllRatingHistory(){
        List<Rating> ratings = ratingService.getAllRatings();
        return ratings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/user/ratings")
    public List<RatingDTO> getUserRatingHistory(){
        List<Rating> ratings = ratingService.getUserRatingHistory();
        return ratings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/public/ratings/{ratingId}")
    public RatingDTO getRatingById(@Valid @PathVariable(value = "ratingId") Long id){
        return convertToDto(ratingService.getRatingById(id));
    }

    @PutMapping("/user/ratings/{ratingId}/edit")
    public RatingDTO updateARating(@Valid @PathVariable(value = "ratingId") Long id, @RequestBody RatingDTO ratingDTO) throws ParseException {
        return convertToDto(ratingService.updateARating(id,convertToEntity(ratingDTO)));
    }

    @DeleteMapping("/admin/ratings/{ratingId}/delete")
    public String deleteARating(@Valid @PathVariable(value = "ratingId") Long id) throws NoMatchUserException {
        if(ratingService.deleteARating(id))
            return "Delete successfully";
        else
            return "Can not delete";
    }

    private RatingDTO convertToDto(Rating rating){
        RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
        ratingDTO.setBookName(rating.getBook().getName());
        ratingDTO.setBookId(rating.getBook().getId());
        ratingDTO.setUsername(rating.getUser().getUsername());
        ratingDTO.setNameOfUser(rating.getUser().getName());
        return ratingDTO;
    }

    private Rating convertToEntity(RatingDTO ratingDTO) throws ParseException {
       Rating rating = modelMapper.map(ratingDTO, Rating.class);
       if(ratingDTO.getBookId() != null && ratingDTO.getUsername() !=null){
        rating.setBook(bookService.getBookById(ratingDTO.getBookId()));
        rating.setUser(userAccountService.getUserByUsername(ratingDTO.getUsername()));
       }
        return rating;

    }
}
