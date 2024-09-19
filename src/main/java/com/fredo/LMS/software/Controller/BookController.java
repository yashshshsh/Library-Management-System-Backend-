package com.fredo.LMS.software.Controller;

import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.service.interfac.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService iBookService;

    @GetMapping("/allBooks")
    public Response fetchAllBooks(){
        return iBookService.fetchAllBooks();
    }

    @GetMapping("/searchQuery/{query}")
    public Response fetchBookQuery(@PathVariable String query){
        return iBookService.fetchQuery(query);
    }

    @PostMapping("/addNewBook")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public Response addNewBook(@RequestBody Book book){
        return iBookService.addNewBook(book);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public Response deleteBook(@PathVariable Long bookId){
        return iBookService.deleteBook(bookId);
    }

    @GetMapping("/findBook/{bookId}")
    public Response findBook(@PathVariable Long bookId){
        return iBookService.findBook(bookId);
    }
}
