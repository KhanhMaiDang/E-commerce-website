package com.example.ecommerceweb.service.implement;

import com.example.ecommerceweb.exception.NoMatchUserException;
import com.example.ecommerceweb.exception.RatingNotFoundException;
import com.example.ecommerceweb.model.*;
import com.example.ecommerceweb.repository.RatingRepository;
import com.example.ecommerceweb.service.BookService;
import com.example.ecommerceweb.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

        Rating rt = ratingRepository.save(newRating);

        float newAvgRating = 0;
        if ( ratingRepository.existsByBook(book)){
            newAvgRating = ratingRepository.calcAvgRating(bookId);
            System.out.println("RATING "+ newAvgRating);
        }
        bookService.updateAvgRating(bookId,newAvgRating);
        return rt;
    }

    public List<Rating> getAllRatingsOfABook(Long bookId){
        return bookService.getAllRatingsOfABook(bookId);
    }

    @Transactional
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

    public Rating updateARating(Long id, Rating newRating){
        return ratingRepository.findById(id).map(rating -> {
            rating.setStar(newRating.getStar());
            Rating rt = ratingRepository.save(rating);
            Float newAvgRating = ratingRepository.calcAvgRating(rating.getBook().getId());
            bookService.updateAvgRating(rating.getBook().getId(),newAvgRating);
            return rt;
        }).orElseThrow(()->new RatingNotFoundException(id));
    }

    public boolean deleteARating(Long id){

        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            System.out.println("RAt "+id);
            ratingRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public Float calcAvgRating(Long bookId){
        return ratingRepository.calcAvgRating(bookId);
    }
}
