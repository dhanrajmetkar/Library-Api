package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    void addAllBooksToDB() throws IOException;

    List<Book> readAllBooks();

    Book updateBook(Long bookid, int copies);

    Optional<Book> findById(Long aLong);

    Book findByTitle(String title);

    void saveBook(Book book);
}
