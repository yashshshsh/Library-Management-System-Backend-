package com.fredo.LMS.software.service.impl;

import com.fredo.LMS.software.Enum.Avaiabilty;
import com.fredo.LMS.software.Utils.Utils;
import com.fredo.LMS.software.dto.BookDto;
import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.repository.BookRepository;
import com.fredo.LMS.software.service.interfac.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Response fetchQuery(String query){
        Response response = new Response();
        try{
            List<Book> bookList = bookRepository.searchBookByName(query);
            List<BookDto> bookDtoList = Utils.mapBookListEntityToBookDtoList(bookList);
            response.setBookDtoList(bookDtoList);
            response.setStatusCode(200);
        } catch(Exception e){
            response.setStatusCode(400);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response fetchAllBooks(){
        Response response = new Response();
        try{
            List<Book> books = bookRepository.findAll();
            List<BookDto> bookDtoList = Utils.mapBookListEntityToBookDtoList(books);
            response.setStatusCode(200);
            response.setBookDtoList(bookDtoList);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response addNewBook(Book newbook){
        Response response = new Response();
        try{
            Book book = new Book();
            book.setBookAuthor(newbook.getBookAuthor());
            book.setBookTitle(newbook.getBookTitle());
            book.setGenre(newbook.getGenre());
            book.setAvailabilty(Avaiabilty.AVAILABLE);
            Book savedBook = bookRepository.save(book);
            response.setStatusCode(200);
            response.setMessage("Added successfully");
            BookDto bookDto = Utils.mapBookEntityToBookDto(savedBook);
            response.setBookDto(bookDto);
            System.out.println("End of book service");
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteBook(Long bookId){
        Response response = new Response();
        try{
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Resource not found"));
            bookRepository.deleteById(bookId);
            response.setMessage("Book deleted succesfully");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBook(Long bookId){
        Response response = new Response();
        try{
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Resource not found"));
            BookDto bookDto = Utils.mapBookEntityToBookDto(book);
            response.setBookDto(bookDto);
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }
}
