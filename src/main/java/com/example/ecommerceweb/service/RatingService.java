package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Rating;
import org.springframework.stereotype.Service;


public interface RatingService {
    public Rating addRating(Long bookId, Rating newRating);
}
