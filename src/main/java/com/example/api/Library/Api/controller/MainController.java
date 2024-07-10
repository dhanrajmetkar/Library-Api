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
       bookService.addAllBooksToDB();
//        memberService.addAllMembersToDB();
//        borrowedBookService.addAllBorrowedBooksToDB();
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

    @GetMapping("/borrowedBooks")
    public List<BorrowedBook> getAllBorrowedBooks(){
       return borrowedBookService.getAllBorrowedBooks();

    }
    @PutMapping("/updateBook")
     Book updateBook(@RequestParam("book_id") Long bookid,@RequestParam("copies") int copies )
    {
     return bookService.updateBook(bookid,copies);
    }

    @GetMapping("/find/{title}")
    public Optional<Book> find(@PathVariable String title)
    {
        return Optional.ofNullable(bookService.findByTitle(title));
    }












}
