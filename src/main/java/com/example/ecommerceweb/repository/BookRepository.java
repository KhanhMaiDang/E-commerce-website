package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}