package com.example.api.Library.Api.repository;

import com.example.api.Library.Api.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook,Long> {
}
