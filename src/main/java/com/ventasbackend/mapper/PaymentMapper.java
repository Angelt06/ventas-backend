package com.ventasbackend.mapper;

import com.ventasbackend.dto.PaymentDTO;
import com.ventasbackend.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setDescription(payment.getDescription());
        //paymentDTO.setSaleId(payment.getSale().getId());
        return paymentDTO;
    }

    public Payment paymentDTOToPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setDescription(paymentDTO.getDescription());
        // Aquí debes asignar la venta a partir del ID, ya que no tienes un SaleDTO con toda la información de la venta
        // Si deseas cargar toda la venta desde la base de datos, deberás inyectar el repositorio correspondiente y obtenerla
        // Puedes hacerlo aquí o en el servicio donde se utilice el mapper
        // payment.setSale(saleRepository.findById(paymentDTO.getSaleId()).orElse(null));
        return payment;
    }
}