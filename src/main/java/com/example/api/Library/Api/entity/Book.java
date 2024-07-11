package com.example.api.Library.Api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String author;
    String genre;
    Integer copies;
    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch =FetchType.EAGER
    )
    @JsonBackReference
    private List<Member> members=new ArrayList<>();

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch =FetchType.EAGER
    )
    @JsonBackReference
    private List<BorrowedBook> borrowed_Book=new ArrayList<>();
}
