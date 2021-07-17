package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {
    public Rating addRating(Long bookId, Rating newRating);
    public List<Rating> getAllRatings();
    public List<Rating> getAllRatingsOfABook(Long bookId);
    public List<Rating> getUserRatingHistory();
    public Rating getRatingById(Long id);
    public Rating updateARating(Long id, Rating newRating);
    public boolean deleteARating(Long id);
}
