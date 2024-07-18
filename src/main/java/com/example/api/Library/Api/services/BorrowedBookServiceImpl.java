package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
                Book book;
                Optional<Book> books = bookService.findById(Long.valueOf(parts[2]));
                if (books.isPresent()) {
                    book = books.get();
                    if(book.getCopies()<=0)
                        throw new RuntimeException("No more copies of this book are available ");
                }
                else{
                    throw new RuntimeException("Book Not found With given Id  :");
                }

                Optional<Member> members = memberService.findById(Long.valueOf(parts[3]));
                if (members.isPresent()) {
                    Member member = members.get();
                    boolean alreadyBorrowed = borrowedBookRepository.existsByMemberAndBook(member, book);

                    if (alreadyBorrowed) {
                        throw new RuntimeException("A member can only borrow one copy of each book at a time.");
                    }

                    book.setCopies(book.getCopies() - 1);
                    borrowedBook.setBook(book);
                    borrowedBook.setMember(member);
                    bookService.saveBook(book);
                }
                else {
                    throw new RuntimeException("Member with given id not found");
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
    public Page<BorrowedBook> getAllBorrowedBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return borrowedBookRepository.findAll(pageable);
    }

    @Override
    public Map<LocalDate,Book> getAllDeuBooks() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

      List<BorrowedBook> borrowedBooks=borrowedBookRepository.findAllBorrowedBook(localDate);

      Map<LocalDate,Book> mp=new TreeMap<>();
      for(BorrowedBook b:borrowedBooks)
      {
             mp.put(b.getReturnDate(),b.getBook());
      }
      if(!mp.isEmpty())
        return mp;
      else
         throw new RuntimeException("No Books due :");
    }

    @Override
    public  BorrowedBook returnBook(int book_id, int mem_id) {

        BorrowedBook b=borrowedBookRepository.findBorrowedBookByBook_idAndMember_id(book_id,mem_id);
        if(b==null)
            throw new RuntimeException("Book not found with given member");

        if(!b.getReturned())
            {
                b.getBook().setCopies(b.getBook().getCopies()+1);
                bookService.saveBook(b.getBook());
                b.setReturned(true);
                b.setBook(null);
                b.setMember(null);
                borrowedBookRepository.save(b);
            }
        else {
            throw new RuntimeException("The given book is already Returned");
            }
        return b;
    }

    @Override
    public boolean readBorrowedBook() {
        return borrowedBookRepository.existsById(Long.valueOf(1));
    }
}