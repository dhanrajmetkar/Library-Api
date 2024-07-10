package com.example.api.Library.Api.entity;

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
    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch =FetchType.EAGER
    )
  private List<Book> book=new ArrayList<>();
 }
