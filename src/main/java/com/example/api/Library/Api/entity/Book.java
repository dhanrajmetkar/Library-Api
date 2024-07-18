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
import java.util.Objects;

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
    private List<BorrowedBook> borrowed_Book=new ArrayList<>();
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title);
    }


}
