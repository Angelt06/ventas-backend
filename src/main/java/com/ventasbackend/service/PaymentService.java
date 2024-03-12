package com.ventasbackend.service;

import com.ventasbackend.dto.PaymentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayments();

    Page<PaymentDTO> getAllPayments(Pageable pageable);

    PaymentDTO findPaymentById(Long paymentId);

    PaymentDTO savePayment(PaymentDTO payment);

    void deletePayment(Long paymentId);
}

