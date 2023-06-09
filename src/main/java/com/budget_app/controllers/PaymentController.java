package com.budget_app.controllers;

import com.budget_app.converters.PaymentConverter;
import com.budget_app.dto.PaymentDto;
import com.budget_app.entities.Payment;
import com.budget_app.services.PaymentService;
import com.budget_app.validators.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentConverter paymentConverter;

    private final PaymentValidator paymentValidator;

    @GetMapping
    public Page<PaymentDto> getAllPayments(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_amount", required = false) Integer minAmount,
            @RequestParam(name = "max_amount", required = false) Integer maxAmount
    ) {
        if (page < 1) {
            page = 1;
        }
        return paymentService.findAll(minAmount, maxAmount, page).map(
                p -> paymentConverter.entityToDto(p)
        );
    }

    @PostMapping
    public PaymentDto saveNewPayment(@RequestBody PaymentDto paymentDto) {
        paymentValidator.validate(paymentDto);
        Payment payment = paymentConverter.dtoToEntity(paymentDto);
        payment = paymentService.save(payment);
        return paymentConverter.entityToDto(payment);
    }

    @PutMapping
    public PaymentDto updatePayment(@RequestBody PaymentDto paymentDto) {
        paymentValidator.validate(paymentDto);
        Payment payment = paymentService.update(paymentDto);
        return paymentConverter.entityToDto(payment);
    }
}
