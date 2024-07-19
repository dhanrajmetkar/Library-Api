package com.example.api.Library.Api.services;

import com.example.api.Library.Api.entity.Book;
import com.example.api.Library.Api.entity.BorrowedBook;
import com.example.api.Library.Api.entity.Member;
import com.example.api.Library.Api.error.ResourceNotFoundException;
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


    @Autowired
    BorrowedBookRepository borrowedBookRepository;
    @Autowired
    BookService bookService;
    @Autowired
    MemberService memberService;


    public List<BorrowedBook> readBorrowedBooksFromFile() throws IOException, ResourceNotFoundException {

        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        String filePath = "/home/nityaobject/Desktop/Spring Boot/Library-Api/upload-dir/borrowedBooks.txt";
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
                        throw new ResourceNotFoundException("No more copies of this book are available ");
                }
                else{
                    throw new ResourceNotFoundException("Book Not found With given Id  :");
                }

                Optional<Member> members = memberService.findById(Long.valueOf(parts[3]));
                if (members.isPresent()) {
                    Member member = members.get();
                    boolean alreadyBorrowed = borrowedBookRepository.existsByMemberAndBook(member, book);

                    if (alreadyBorrowed) {
                        throw new ResourceNotFoundException("A member can only borrow one copy of each book at a time.");
                    }

                    book.setCopies(book.getCopies() - 1);
                    borrowedBook.setBook(book);
                    borrowedBook.setMember(member);
                    bookService.saveBook(book);
                }
                else {
                    throw new ResourceNotFoundException("Member with given id not found");
                }
                borrowedBooks.add(borrowedBook);
            }
        }
        reader.close();
        return borrowedBooks;

    }

    public void addAllBorrowedBooksToDB() throws IOException, ResourceNotFoundException {
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
    public  BorrowedBook returnBook(int book_id, int mem_id) throws ResourceNotFoundException {

        BorrowedBook b=borrowedBookRepository.findBorrowedBookByBook_idAndMember_id(book_id,mem_id);
        if(b==null)
            throw new ResourceNotFoundException("Book not found with given member or member does not exist ");

        if(!b.getReturned())
            {
                b.getBook().setCopies(b.getBook().getCopies()+1);
                bookService.saveBook(b.getBook());
                b.setReturned(true);
                borrowedBookRepository.save(b);
            }
        else {
            throw new ResourceNotFoundException("The given book is already Returned");
            }
        return b;
    }

    @Override
    public boolean readBorrowedBook() {
        return borrowedBookRepository.existsById(1L);
    }

    @Override
    public Map<LocalDate, List<Book>> getAllDeuBooks() throws ResourceNotFoundException {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        List<BorrowedBook> borrowedBooks=borrowedBookRepository.findAllBorrowedBook(localDate);
        Map<LocalDate, List<Book>> mp = insertIntoMap(borrowedBooks);
        if (!mp.isEmpty()) {
            return mp;
        }
        else
            throw new ResourceNotFoundException("No Books due :");
    }

    @Override
    public Map<LocalDate, List<Book>> getAllDeuBooksByDate(LocalDate date) throws ResourceNotFoundException {

        List<BorrowedBook> borrowedBooks=borrowedBookRepository.findAllBorrowedBookDate(date);
        if(!borrowedBooks.isEmpty()) {
            return insertIntoMap(borrowedBooks);
        }
        else {
            throw new ResourceNotFoundException("No books due for the given date :");
        }
    }

    @Override
    public String checkAvailibilityofBook(Book book) {
        LocalDate localDate=null;
        List<BorrowedBook> borrowedBooks=borrowedBookRepository.findByBook_id(book.getId());
        for(BorrowedBook borrowedBook:borrowedBooks)
        {
            if(localDate==null)
            {
                localDate=borrowedBook.getReturnDate();
            }
            else {
                if(localDate.isAfter(borrowedBook.getReturnDate()))
                {
                    localDate=borrowedBook.getReturnDate();
                }
            }
        }
        assert localDate != null;

        return "you will get the book by "+ localDate;
    }

    @Override
    public List<BorrowedBook> checkBookInfo(Long id) {
        return borrowedBookRepository.findByBook_id(id);
    }

    private Map<LocalDate, List<Book>> insertIntoMap(List<BorrowedBook> borrowedBooks) {
        Map<LocalDate, List<Book>> mp=new HashMap<>();
        borrowedBooks.forEach(borrowedBook ->
        {
            if (!borrowedBook.getReturned()) {
                if (mp.containsKey(borrowedBook.getReturnDate())) {
                    mp.get(borrowedBook.getReturnDate()).add(borrowedBook.getBook());
                } else {
                    mp.put(borrowedBook.getReturnDate(), new ArrayList<>());
                    mp.get(borrowedBook.getReturnDate()).add(borrowedBook.getBook());
                }
            }
        });
        return mp;
    }
}
