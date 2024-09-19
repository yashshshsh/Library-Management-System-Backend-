package com.fredo.LMS.software.service.interfac;

import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.Book;

public interface IBookService {

    Response fetchQuery(String query);

    Response fetchAllBooks();

    Response addNewBook(Book newbook);

    Response deleteBook(Long bookId);

    Response findBook(Long bookId);
}
