package com.example.api.Library.Api.repository;

import com.example.api.Library.Api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findAllById(Long bookid);

    Book findByTitleIgnoreCase(String title);

}
