package com.fredo.LMS.software.dto;

import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowDto {
    private BookDto bookDto;
    private UserDto userDto;
    private LocalDate borrowDate;
    private LocalDate dueDate;
}
