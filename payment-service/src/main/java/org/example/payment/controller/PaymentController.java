package org.example.payment.controller;

import org.example.common.PaymentStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {

    @GetMapping("/payments/status")
    public PaymentStatus status() {
        return PaymentStatus.CREATED;
    }
}
