package com.fredo.LMS.software.Utils;

import com.fredo.LMS.software.dto.BookDto;
import com.fredo.LMS.software.dto.BorrowDto;
import com.fredo.LMS.software.dto.UserDto;
import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.model.Borrowing;
import com.fredo.LMS.software.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static UserDto mapUserEntityToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getUserRole());
        return userDto;
    }

    public static BookDto mapBookEntityToBookDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setBookTitle(book.getBookTitle());
        bookDto.setBookId(book.getBookId());
        bookDto.setGenere(book.getGenre());
        bookDto.setBookAuthor(book.getBookAuthor());
        bookDto.setAvailabilty(book.getAvailabilty().toString());
        return bookDto;
    }

    public static BorrowDto mapBorrowEntityToBorrowDto(Borrowing borrow){
        BorrowDto borrowDto = new BorrowDto();
        UserDto userDto = mapUserEntityToUserDto(borrow.getUser());
        BookDto bookDto = mapBookEntityToBookDto(borrow.getBook());
        borrowDto.setBookDto(bookDto);
        borrowDto.setUserDto(userDto);
        borrowDto.setBorrowDate(borrow.getBorrowDate());
        borrowDto.setDueDate(borrow.getDueDate());
        return borrowDto;
    }

    public static List<UserDto> mapUserListEntityToUserDtoList(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDto).collect(Collectors.toList());
    }

    public static List<BookDto> mapBookListEntityToBookDtoList(List<Book> bookList){
        return bookList.stream().map(Utils::mapBookEntityToBookDto).collect(Collectors.toList());
    }

}
