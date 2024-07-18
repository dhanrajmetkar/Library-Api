package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> readBooksFromFile() throws IOException {
        List<Book> books = new ArrayList<>();
        String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/books1.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {

            String[] parts = line.trim().split(",");
            if (parts.length >= 3) {
                Book book = new Book();
                book.setTitle(parts[0].trim());
                book.setAuthor(parts[1].trim());
                book.setGenre(parts[2].trim());
                book.setCopies(Integer.valueOf(parts[3].trim()));
                books.add(book);
            }
        }
        reader.close();
        return books;

    }

    @Override
    public void addAllBooksToDB() throws IOException {
        List<Book> books = readBooksFromFile();
        for (Book book : books) {
            bookRepository.save(book);
        }
    }

    @Override
    public Page<Book> readAllBooks(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public Book updateBook(Long bookid, int copies) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        return bookRepository.findById(aLong);
    }

    @Override
    public Book findByTitle(String title) {
        Book book = bookRepository.findByTitleIgnoreCase(title);
        return book;
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Boolean readBook() {
        return bookRepository.existsById(Long.valueOf(1));
    }
}
