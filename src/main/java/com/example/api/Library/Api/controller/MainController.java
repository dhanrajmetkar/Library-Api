package com.example.api.Library.Api.controller;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.services.BookService;
import com.example.api.Library.Api.services.BorrowedBookService;
import com.example.api.Library.Api.services.MemberService;
import com.example.api.Library.Api.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowedBookService borrowedBookService;
    @Autowired
    MemberService memberService;
    @Autowired
    StorageService storageService;

    @PostMapping(value = "/upload",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allFiles(@RequestParam("files")MultipartFile[] files) throws IOException {

        storageService.store(files[0]);
        storageService.store(files[1]);
        storageService.store(files[2]);
        List<Book> books = bookService.readAllBooks();
        if (books.isEmpty()) {
            bookService.addAllBooksToDB();
        }
        List<Member> members = memberService.getAllMembers();
        if (members.isEmpty()) {
            memberService.addAllMembersToDB();
        }
        List<BorrowedBook> borrowedBooks=borrowedBookService.getAllBorrowedBooks();
        if(borrowedBooks.isEmpty()){
            borrowedBookService.addAllBorrowedBooksToDB();
        }

        return ResponseEntity.ok("file Uploaded successfully ");
    }

    @GetMapping("/allBooks")
    public List<Book> readAllBooks()
    {
        return bookService.readAllBooks();
    }

    @GetMapping("/allMembers")
    public List<Member> getAllMembers(){
     return  memberService.getAllMembers();
    }

    @GetMapping("/allBorrowedBooks")
    public List<BorrowedBook> getAllBorrowedBooks(){
       return borrowedBookService.getAllBorrowedBooks();

    }

}





