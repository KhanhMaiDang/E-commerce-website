package com.example.ecommerceweb.DTO;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter @Setter
public class BookDTO {
    private String name;

    private String author;

    private String publisher;

    private String description;

    private String category;

    private Double price;

    private String imageUrl;

}
