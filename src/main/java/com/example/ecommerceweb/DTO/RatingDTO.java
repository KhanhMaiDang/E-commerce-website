package com.example.ecommerceweb.DTO;

import com.example.ecommerceweb.model.Book;
import com.example.ecommerceweb.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter
@JsonIgnoreProperties(
        value = {"createdAt, updatedAt"},
        allowGetters = true
)
public class RatingDTO {

    private Long id;

    private String bookName;
    private Long bookId;

    private String nameOfUser;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    @Min(value = 0)
    @Max(value = 5)
    @NotNull
    private Float star;

}
