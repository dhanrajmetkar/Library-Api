package com.example.api.Library.Api.controller;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.error.ResourceNotFoundException;
import com.example.api.Library.Api.services.BookService;
import com.example.api.Library.Api.services.BorrowedBookService;
import com.example.api.Library.Api.services.MemberService;
import com.example.api.Library.Api.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allFiles(@RequestParam("files") MultipartFile[] files) throws IOException, ResourceNotFoundException {

        storageService.store(files[0]);
        storageService.store(files[1]);
        storageService.store(files[2]);
        if (!bookService.readBook()) {
            bookService.addAllBooksToDB();
        }
        if (!memberService.readMember()) {
            memberService.addAllMembersToDB();
        }
        if (borrowedBookService.readBorrowedBook()) {
            borrowedBookService.addAllBorrowedBooksToDB();
        }
        return ResponseEntity.ok("file Uploaded successfully ");
    }

    @GetMapping("/allBooks/{pageNo}")
    public ResponseEntity<List<Book>> readAllBooks(@PathVariable("pageNo") Integer pageNo) {
        int pageSize = 3;
        Page<Book> booksPage = bookService.readAllBooks(pageNo, pageSize);
        List<Book> books = booksPage.getContent();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/allMembers/{pageNo}")
    public ResponseEntity<List<Member>> getAllMembers(@PathVariable("pageNo") int pageNo) {
        int pageSize = 3;
        Page<Member> membersPage = memberService.getAllMembers(pageNo, pageSize);
        List<Member> memberList = membersPage.getContent();
        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/allBorrowedBooks/{pageNo}")
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBooks(@PathVariable("pageNo") int pageNo) {
        int pageSize = 3;
        Page<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooks(pageNo, pageSize);
        List<BorrowedBook> borrowedBookList = borrowedBooks.getContent();
        return ResponseEntity.ok(borrowedBookList);
    }

    @GetMapping("/dueBooks")
    public Map<LocalDate, List<Book>> deuBooks() throws ResourceNotFoundException {
        return borrowedBookService.getAllDeuBooks();
    }
    @GetMapping("/dueBooksByDate/{date}")
    public Map<LocalDate, List<Book>> deuBooksByDate(@PathVariable("date")LocalDate date) throws ResourceNotFoundException {
        return borrowedBookService.getAllDeuBooksByDate(date);
    }
    @PostMapping("/returnBook")
    public BorrowedBook returnBook(@Param("book_id") int bookId, @RequestParam("member_id") int mem_id) throws ResourceNotFoundException {
        return borrowedBookService.returnBook(bookId, mem_id);
    }
    @GetMapping("/borrowBook/{title}")
    public String borrowBook(@PathVariable("title") String title ) throws ResourceNotFoundException {
        Book book=bookService.findByTitle(title.trim());
        if(book==null)
            throw new ResourceNotFoundException("book with given title not found :");
        if(book.getCopies()>0) {
            return "You can borrow books :";
        }
        else
        {
            return borrowedBookService.checkAvailibilityofBook(book);
        }
    }
    @GetMapping("/checkBookInfo/{id}")
    public  ResponseEntity<?> bookInfo(@PathVariable("id") Long id ) throws ResourceNotFoundException {
        List<BorrowedBook>  borrowedBooks= borrowedBookService.checkBookInfo(id);
        if(!borrowedBooks.isEmpty())
            return ResponseEntity.of(Optional.of(borrowedBooks));
        Optional<Book> book=bookService.findById(id);
        if(book.isEmpty())
             throw new ResourceNotFoundException("book not found with given id");
        return ResponseEntity.of(book);
    }
}





