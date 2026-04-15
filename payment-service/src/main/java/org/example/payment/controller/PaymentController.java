package org.example.payment.controller;

import java.math.BigDecimal;
import java.util.UUID;
import org.example.common.Payment;
import org.example.common.PaymentStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {

    @GetMapping("/payments/demo")
    public Payment status() {
        return new Payment(
            UUID.randomUUID(),
            BigDecimal.valueOf(1000),
            PaymentStatus.CREATED
        );
    }
}
