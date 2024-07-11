package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {


    private final String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/borrowedBooks.txt";

    @Autowired
    BorrowedBookRepository borrowedBookRepository;
    @Autowired
    BookService bookService;
    @Autowired
    MemberService memberService;

    public List<BorrowedBook> readBorrowedBooksFromFile() throws IOException {

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {

            String[] parts = line.trim().split(",");
            if (parts.length >= 3) {
                BorrowedBook borrowedBook = new BorrowedBook();
                borrowedBook.setBorrowedDate(LocalDate.parse(parts[0].trim()));
                borrowedBook.setReturnDate(LocalDate.parse(parts[1].trim()));
                Book book = null;
                Optional<Book> books = bookService.findById(Long.valueOf(parts[2]));
                if (books.isPresent()) {
                    book = books.get();

                }

                Optional<Member> members = memberService.findById(Long.valueOf(parts[3]));
                if (members.isPresent()) {
                    Member member = members.get();
                    member.setBook(book);
                    book.setCopies(book.getCopies() - 1);
                    borrowedBook.setBook(book);
                    borrowedBook.setMember(member);
                    bookService.saveBook(book);
                    memberService.saveMember(member);
                }
                borrowedBooks.add(borrowedBook);
            }
        }
        reader.close();
        return borrowedBooks;

    }

    public void addAllBorrowedBooksToDB() throws IOException {
        List<BorrowedBook> BorrowedBooks = readBorrowedBooksFromFile();
        for (BorrowedBook borrowedBook : BorrowedBooks) {
            borrowedBookRepository.save(borrowedBook);
        }
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }


}
