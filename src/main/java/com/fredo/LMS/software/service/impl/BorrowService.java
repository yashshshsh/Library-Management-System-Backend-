package com.fredo.LMS.software.service.impl;

import com.fredo.LMS.software.Enum.Avaiabilty;
import com.fredo.LMS.software.Enum.PaymentStatus;
import com.fredo.LMS.software.Utils.Utils;
import com.fredo.LMS.software.dto.BorrowDto;
import com.fredo.LMS.software.dto.Response;
import com.fredo.LMS.software.model.Book;
import com.fredo.LMS.software.model.Borrowing;
import com.fredo.LMS.software.model.Fine;
import com.fredo.LMS.software.model.User;
import com.fredo.LMS.software.repository.BookRepository;
import com.fredo.LMS.software.repository.BorrowRepository;
import com.fredo.LMS.software.repository.UserRepository;
import com.fredo.LMS.software.service.interfac.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowService implements IBorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    private static final long EXPIRATION_DAYS = 7;

    @Override
    public Response newBorrowing(Long bookId) {
        Response response = new Response();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception(email+ " not found"));
            Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new Exception(bookId + " not found"));
            Borrowing borrow = new Borrowing();
            borrow.setBook(book);
            borrow.setUser(user);
            borrow.setBorrowDate(LocalDate.now());
            borrow.setDueDate(LocalDate.now().plusDays(EXPIRATION_DAYS));
            borrow.setUser(user);
            borrowRepository.save(borrow);
            book.setAvailabilty(Avaiabilty.ISSUED);
            book.setIssuedDate(LocalDate.now());
            book.setAvailableDate(LocalDate.now().plusDays(EXPIRATION_DAYS));
            book.setUser(user);
            bookRepository.save(book);
            BorrowDto borrowDto = Utils.mapBorrowEntityToBorrowDto(borrow);
            response.setStatusCode(200);
            response.setBorrowDto(borrowDto);
            response.setMessage("Borrowed successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response returnBook(Long userId,Long bookId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception(userId + " not found"));
            Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new Exception(bookId + " not found"));
            Borrowing borrow = borrowRepository.findByBookAndUser(book, user).orElseThrow(() -> new Exception("Borrowing not found"));
            borrow.setReturnDate(LocalDate.now());
            book.setAvailabilty(Avaiabilty.AVAILABLE);
            book.setAvailableDate(LocalDate.now());
            book.setIssuedDate(null);
            bookRepository.save(book);
            double fineAmt = borrow.calculateFine(borrow.getBorrowDate(),borrow.getDueDate(),borrow.getReturnDate());
            Fine fine = borrow.getFine() != null ? borrow.getFine() : new Fine();
            fine.setBorrow(borrow);
            fine.setAmount(fineAmt);
            fine.setPaymentStatus(PaymentStatus.PAID);
            borrow.setFine(fine);
            borrowRepository.save(borrow);
            BorrowDto borrowDto = Utils.mapBorrowEntityToBorrowDto(borrow);
            response.setStatusCode(200);
            response.setBorrowDto(borrowDto);
            response.setMessage("Returned successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response renewBorrowing(Long borrowId) {
        Response response = new Response();
        try {
            Borrowing borrow = borrowRepository.findByBorrowId(borrowId).orElseThrow(() -> new Exception("Resource not found!!"));
            Book book = borrow.getBook();
            if(book.getAvailabilty().equals(Avaiabilty.AVAILABLE)) {
                borrow.setBorrowDate(LocalDate.now());
                borrow.setDueDate(LocalDate.now().plusDays(EXPIRATION_DAYS));
                borrowRepository.save(borrow);
                book.setIssuedDate(LocalDate.now());
                book.setAvailableDate(LocalDate.now().plusDays(EXPIRATION_DAYS));
                book.setAvailabilty(Avaiabilty.ISSUED);
                bookRepository.save(book);
                BorrowDto borrowDto = Utils.mapBorrowEntityToBorrowDto(borrow);
                response.setStatusCode(200);
                response.setBorrowDto(borrowDto);
                response.setMessage("Renewed successfully");
            } else {
                response.setMessage("Not available");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error " + e.getMessage());
        }
        return response;
    }
}
