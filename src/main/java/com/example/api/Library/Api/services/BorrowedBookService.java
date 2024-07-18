package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
public interface BorrowedBookService {
    void addAllBorrowedBooksToDB() throws IOException;

    Page<BorrowedBook> getAllBorrowedBooks(int pageNo, int pageSize);


    Map<LocalDate,Book> getAllDeuBooks();


    BorrowedBook returnBook(int b_id, int mem_id);

    boolean readBorrowedBook();
}
