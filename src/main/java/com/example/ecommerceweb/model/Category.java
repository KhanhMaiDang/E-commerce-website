package com.example.ecommerceweb.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "category")
public class Category extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Collection<Book> books;
}
