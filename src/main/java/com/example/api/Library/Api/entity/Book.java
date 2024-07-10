package com.example.api.Library.Api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
@ManyToOne
@JoinColumn(name = "member_id")
    Member member;
}
