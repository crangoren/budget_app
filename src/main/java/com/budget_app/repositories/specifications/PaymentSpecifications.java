package com.budget_app.repositories.specifications;

import com.budget_app.entities.Payment;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PaymentSpecifications {

    public static Specification<Payment> amountGreaterOrEqualsThan(Integer amount) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amount);
    }

    public static Specification<Payment> amountLessThanOrEqualsThan(Integer amount) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amount);
    }

//    public static Specification<Payment> date(Date date) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
//    }
}
