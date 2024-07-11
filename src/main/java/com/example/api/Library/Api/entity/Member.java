package com.example.api.Library.Api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String Name;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "book_Id", referencedColumnName = "id")
    @JsonManagedReference
    private Book book;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch =FetchType.EAGER
    )
    @JsonBackReference
    private List<BorrowedBook> borrowed_Book=new ArrayList<>();

 }
