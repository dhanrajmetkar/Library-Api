package com.example.api.Library.Api.entity;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_id", referencedColumnName = "id")
    private Member member;

}
