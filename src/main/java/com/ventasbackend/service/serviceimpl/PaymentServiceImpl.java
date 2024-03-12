package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.dto.PaymentDTO;
import com.ventasbackend.entity.Payment;
import com.ventasbackend.mapper.PaymentMapper;
import com.ventasbackend.repository.PaymentRepository;
import com.ventasbackend.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findAll(pageable);
        return paymentsPage.map(paymentMapper::paymentToPaymentDTO);
    }

    @Override
    public PaymentDTO findPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        return (payment == null) ? null : paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDTO(savedPayment);
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
