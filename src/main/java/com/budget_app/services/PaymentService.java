package com.budget_app.services;

import com.budget_app.dto.PaymentDto;
import com.budget_app.entities.Payment;
import com.budget_app.exceptions.ResourceNotFoundException;
import com.budget_app.repositories.PaymentRepository;
import com.budget_app.repositories.specifications.PaymentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Page<Payment> findAll(Integer minAmount, Integer maxAmount, Integer page) {
        Specification<Payment> spec = Specification.where(null);
        if (minAmount != null) {
            spec = spec.and(PaymentSpecifications.amountGreaterOrEqualsThan(minAmount));
        }
        if (maxAmount != null) {
            spec = spec.and(PaymentSpecifications.amountLessThanOrEqualsThan(maxAmount));
        }

        return paymentRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment update(PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Платеж не найден, id: " + paymentDto.getId()));
        payment.setAmount(paymentDto.getAmount());
        payment.setDescription(paymentDto.getDescription());
        return payment;
    }

    public Page<Payment> findAllByDate(Date date, Integer page) {

        return paymentRepository.findAll(PageRequest.of(page - 1, 8));
    }
}
