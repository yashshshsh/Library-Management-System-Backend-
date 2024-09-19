package com.fredo.LMS.software.model;

import com.fredo.LMS.software.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fine")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fineId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "borrow_ID")
    private Borrowing borrow;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
