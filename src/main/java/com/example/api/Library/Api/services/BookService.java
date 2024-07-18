package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface BookService {
    void addAllBooksToDB() throws IOException;

    Page<Book> readAllBooks(int pageNumber, int pageSize);

    Optional<Book> findById(Long aLong);

    void saveBook(Book book);

    Boolean readBook();

    Book findByTitle(String title);
}
