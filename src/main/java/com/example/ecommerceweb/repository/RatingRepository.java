package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("select avg(star) from Rating where book.id= ?1")
    public float calcAvgRating(Long bookId);

    public List<Rating> findByBook(Book book);
    public boolean existsByBook(Book book);
    public void deleteById(Long ratingId);
}
