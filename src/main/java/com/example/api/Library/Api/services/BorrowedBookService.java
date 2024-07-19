package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.error.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface BorrowedBookService {
    void addAllBorrowedBooksToDB() throws IOException, ResourceNotFoundException;

    Page<BorrowedBook> getAllBorrowedBooks(int pageNo, int pageSize);


    Map<LocalDate, List<Book>> getAllDeuBooks() throws ResourceNotFoundException;


    BorrowedBook returnBook(int b_id, int mem_id) throws ResourceNotFoundException;

    boolean readBorrowedBook();

    Map<LocalDate, List<Book>> getAllDeuBooksByDate(LocalDate date) throws ResourceNotFoundException;

    String checkAvailibilityofBook(Book book);

    List<BorrowedBook> checkBookInfo(Long id);
}
