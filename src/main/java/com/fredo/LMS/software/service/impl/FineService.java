package com.fredo.LMS.software.service.impl;

import com.fredo.LMS.software.Enum.PaymentStatus;
import com.fredo.LMS.software.model.Borrowing;
import com.fredo.LMS.software.model.Fine;
import com.fredo.LMS.software.repository.BorrowRepository;
import com.fredo.LMS.software.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private FineRepository fineRepository;

    private Borrowing borrowing;

    public void calculateFineDaily(){
        List<Borrowing> borrowList = borrowRepository.findAll();
        for(Borrowing borrow:borrowList){
            double amount = borrowing.calculateFine(borrow.getBorrowDate(),borrow.getDueDate(),borrow.getReturnDate());
            if(amount != 0.0){
                Fine fine = new Fine();
                fine.setAmount(amount);
                fine.setPaymentStatus(PaymentStatus.UNPAID);
                fine.setBorrow(borrow);
                fineRepository.save(fine);
            } else {
                Fine fine = new Fine();
                fine.setAmount(0.0);
                fine.setPaymentStatus(PaymentStatus.PAID);
                fineRepository.save(fine);
            }
        }
    }
}
