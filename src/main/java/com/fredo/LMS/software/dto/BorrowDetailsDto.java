package com.fredo.LMS.software.dto;

import com.fredo.LMS.software.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetailsDto {

    private long borrowId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String bookAuthor;
    private String genre;
    private LocalDate returnDate;
    private Double amount;
    private PaymentStatus paymentStatus;

    public BorrowDetailsDto(long borrowId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate,
                            String bookAuthor, String bookTitle, String genre, Double amount, PaymentStatus paymentStatus) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}
