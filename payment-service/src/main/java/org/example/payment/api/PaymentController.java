package org.example.payment.api;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.payment.api.dto.CreatePaymentRequest;
import org.example.payment.api.dto.PaymentDto;
import org.example.payment.application.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto create(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/{id}")
    public PaymentDto get(@PathVariable UUID id) {
        return paymentService.getPayment(id);
    }

    @PostMapping("/{id}/confirm")
    public PaymentDto confirm(@PathVariable UUID id) {
        return paymentService.confirmPayment(id);
    }
}
