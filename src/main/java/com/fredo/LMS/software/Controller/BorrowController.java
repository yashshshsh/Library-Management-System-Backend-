package com.fredo.LMS.software.Controller;

import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.service.interfac.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private IBorrowService iBorrowService;

    @PostMapping("/newBorrow/{bookId}")
    public Response newBorrowings(@PathVariable Long bookId){
        return iBorrowService.newBorrowing(bookId);
    }

    @PostMapping("/returnBorrow/{userId}/{bookId}")
    public Response returnBook(@PathVariable Long userId,@PathVariable Long bookId){
        return iBorrowService.returnBook(userId,bookId);
    }

    @PostMapping("/renewBorrow/{borrowId}")
    public Response renewBorrow(@PathVariable Long borrowId){
        return iBorrowService.renewBorrowing(borrowId);
    }
}
