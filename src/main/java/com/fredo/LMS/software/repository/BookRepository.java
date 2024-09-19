package com.fredo.LMS.software.repository;

import com.fredo.LMS.software.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    void deleteById(Long bookId);

    Optional<Book> findByBookId(Long bookId);

    @Query("SELECT B FROM Book B WHERE lower(B.bookTitle) LIKE lower(concat(:query, '%'))")
    List<Book> searchBookByName(@Param("query") String query);

}
