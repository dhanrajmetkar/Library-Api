package com.example.api.Library.Api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    LocalDate borrowedDate;
    LocalDate returnDate;
    Boolean returned=false;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "book_Id", referencedColumnName = "id")
    @JsonManagedReference
    private Book book;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "member_Id", referencedColumnName = "id")
    @JsonManagedReference
    private Member member;

}
