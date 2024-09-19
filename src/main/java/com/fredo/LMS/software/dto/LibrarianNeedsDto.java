package com.fredo.LMS.software.dto;

import com.fredo.LMS.software.Enum.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LibrarianNeedsDto {
    private long borrowId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String bookAuthor;
    private String genre;
    private LocalDate returnDate;
    private double amount;
    private PaymentStatus paymentStatus;
    private long userId;
    private String name;
    private String userRole;

    public LibrarianNeedsDto(long borrowId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate,
                             String bookAuthor, String bookTitle, String genre, double amount,
                             PaymentStatus paymentStatus, long userId, String name, String userRole) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.name = name;
        this.userRole = userRole;
    }

}
