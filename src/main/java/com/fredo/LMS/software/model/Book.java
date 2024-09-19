package com.fredo.LMS.software.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fredo.LMS.software.Enum.Avaiabilty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "books")
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @NonNull
    private String bookTitle;

    @NonNull
    private String genre;

    @NonNull
    private String bookAuthor;

    private LocalDate issuedDate;

    private LocalDate availableDate;

    @Enumerated(EnumType.STRING)
    private Avaiabilty availabilty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<Borrowing> borrowings = new ArrayList<>();
}
