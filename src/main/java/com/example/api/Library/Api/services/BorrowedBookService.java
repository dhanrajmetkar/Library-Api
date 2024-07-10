package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.BorrowedBook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BorrowedBookService {
    void addAllBorrowedBooksToDB() throws IOException;

    List<BorrowedBook> getAllBorrowedBooks();
}
