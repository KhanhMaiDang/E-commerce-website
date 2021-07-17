package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.exception.RatingNotFoundException;
import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.Rating;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.RatingRepository;
import com.example.ecommerceweb.service.BookService;
import com.example.ecommerceweb.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private BookService bookService;

    public Rating addRating(Long bookId, Rating newRating){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = ((CustomUserDetail)auth.getPrincipal());
        User currentUser = customUserDetail.getUser();
        newRating.setUser(currentUser);

        Book book = bookService.getBookById(bookId);
        newRating.setBook(book);

        float newAvgRating = 0;
        if ( ratingRepository.existsByBook(book)){
            newAvgRating = ratingRepository.calcAvgRating(bookId);
            System.out.println("RATING "+ newAvgRating);
        }
        bookService.updateAvgRating(bookId,newAvgRating);
        return ratingRepository.save(newRating);
    }

    public List<Rating> getAllRatingsOfABook(Long bookId){
        return bookService.getAllRatingsOfABook(bookId);
    }

    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    public List<Rating> getUserRatingHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = ((CustomUserDetail)auth.getPrincipal());
        User currentUser = customUserDetail.getUser();

        return currentUser.getMyRatings();
    }

    public Rating getRatingById(Long id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if(rating.isPresent()){
            return rating.get();
        }
        else
            throw new RatingNotFoundException(id);

    }
}
