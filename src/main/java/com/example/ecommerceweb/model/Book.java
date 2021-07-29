package com.example.ecommerceweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter
public class Book extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_book_category")
    private Category category;
    @Column
    private Double price;
    @Column
    private Integer remaining;

    //@Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name="image",columnDefinition = "bytea")
    private byte[] image;

    @PrePersist
    public void prePersist() {
        if(avgRating == null) //We set default value in case if the value is not set yet.
            avgRating = 0F;
    }
    @Column(columnDefinition = "float4 default 0",nullable = false)
    private Float avgRating = 0F;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratings;

//    public Book copyABook(Book book){
//        this.id = book.id;
//        this.name = book.name;
//        this.author= book.author;
//        this.publisher = book.publisher;
//        this.description = book.description;
//        this.category = book.category
//    }

}
