package com.example.ecommerceweb.DTO;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter
@JsonIgnoreProperties(
        value = {"createdAt, updatedAt"},
        allowGetters = true
)
public class BookDTO {
    @NotNull
    private String name;
    @NotNull
    private String author;
    @NotNull
    private String publisher;

    private String description;

    private String category;
    @NotNull
    private Double price;
    @NotNull
    private Integer remaining;

    private String imageUrl;

    private Date createdAt;

    private Date updatedAt;

}
