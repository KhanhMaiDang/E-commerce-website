package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    public Book findBookByName(String name);
    public Book findBookByAuthor(String author);
    //public Book findBookByCategory(String author);

}
