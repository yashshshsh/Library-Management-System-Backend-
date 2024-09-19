package com.fredo.LMS.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String Availability;

    private UserDto userDto;
    private BookDto bookDto;
    private BorrowDto borrowDto;

    private List<UserDto> userDtoList;
    private List<BookDto> bookDtoList;
    private List<BorrowDetailsDto> borrowingDetails;
    private List<LibrarianNeedsDto> librarianNeedsDtos;
}

