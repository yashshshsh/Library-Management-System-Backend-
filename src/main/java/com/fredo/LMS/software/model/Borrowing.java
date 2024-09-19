package com.fredo.LMS.software.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "borrowings")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long borrowId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    @ToString.Exclude
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @OneToOne(mappedBy = "borrow",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Fine fine;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private LocalDate dueDate;

    public long daysTillDate(LocalDate fromDate, LocalDate toDate){
        return ChronoUnit.DAYS.between(fromDate,toDate);
    }

    public double calculateFine(LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate){
        if(returnDate.isBefore(dueDate)){
            double finePerDay = 5.0;
            long overDueDate = daysTillDate(dueDate,returnDate);
            return overDueDate*finePerDay;
        }
        return 0.0;
    }
}
