package com.budget_app.converters;

import com.budget_app.dto.PaymentDto;
import com.budget_app.entities.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConverter {

    public Payment dtoToEntity(PaymentDto paymentDto) {
        return new Payment(paymentDto.getId(), paymentDto.getAmount(), paymentDto.getPaymentDate(), paymentDto.getIsMonthly(), paymentDto.getDescription(), paymentDto.getUserId());
    }

    public PaymentDto entityToDto(Payment payment) {
        return new PaymentDto(payment.getId(), payment.getAmount(), payment.getPaymentDate(), payment.getIsMonthly(), payment.getDescription(), payment.getUserId());
    }
}
