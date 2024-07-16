package com.example.api.Library.Api.repository;

import com.example.api.Library.Api.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    @Query(" Select b from BorrowedBook b where b.returnDate >= :localDate "  )
    List<BorrowedBook> findAllBorrowedBook(@Param("localDate")LocalDate localDate);
}
