package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface BorrowedBookService {
    void addAllBorrowedBooksToDB() throws IOException;

    List<BorrowedBook> getAllBorrowedBooks();


    Map<LocalDate,Book> getAllDeuBooks();


    BorrowedBook returnBook(int b_id, int mem_id);
}
