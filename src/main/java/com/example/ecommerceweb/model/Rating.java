package com.example.ecommerceweb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ratings")
@Getter @Setter
public class Rating extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_rating_book")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_rating_user")
    private User user;

    @Min(value = 0)
    @Max(value = 5)
    private Float star;

}
