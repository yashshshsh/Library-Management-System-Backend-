package com.fredo.LMS.software.repository;

import com.fredo.LMS.software.dto.BorrowDetailsDto;
import com.fredo.LMS.software.dto.LibrarianNeedsDto;
import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.model.Borrowing;
import com.fredo.LMS.software.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrowing,Long> {
    Optional<Borrowing> findByBookAndUser(Book book, User user);

    Optional<Borrowing> findByBorrowId(Long borrowId);

    @Query("SELECT new com.fredo.LMS.software.dto.BorrowDetailsDto(b.borrowId, b.borrowDate, b.dueDate, b.returnDate, " +
            "book.bookAuthor, book.bookTitle, book.genre, f.amount, f.paymentStatus) " +
            "FROM Borrowing b " +
            "JOIN b.user u " +
            "JOIN b.book book " +
            "LEFT JOIN b.fine f " +  // Including Fine details
            "WHERE u.id = :userId")
    List<BorrowDetailsDto> findByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.fredo.LMS.software.dto.LibrarianNeedsDto(bo.borrowId, bo.borrowDate, bo.dueDate, COALESCE(bo.returnDate,'null'), " +
            "b.bookAuthor, b.bookTitle, b.genre, COALESCE(f.amount, 0.0), COALESCE(f.paymentStatus, 'PENDING'), u.id, u.name, u.userRole) " +
            "FROM Borrowing bo " +
            "JOIN bo.user u " +
            "JOIN bo.book b " +
            "LEFT JOIN bo.fine f")
    List<LibrarianNeedsDto> findLibrarianNeeds();

}
