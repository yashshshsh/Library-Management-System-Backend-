package com.fredo.LMS.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long bookId;

    private String bookTitle;

    private String genere;

    private String bookAuthor;

    private String Availabilty;
}
