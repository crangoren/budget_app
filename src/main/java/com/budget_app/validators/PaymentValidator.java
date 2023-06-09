package com.budget_app.validators;

import com.budget_app.dto.PaymentDto;
import com.budget_app.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentValidator {

    public void validate(PaymentDto paymentDto) {
        List<String> errors = new ArrayList<>();
        if (paymentDto.getAmount() < 1) {
            errors.add("Платеж не может быть меньше 1");
        }
//        if (paymentDto.getDate().isBlank()) {
//            errors.add("Дата не может быть пустой");
//        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
