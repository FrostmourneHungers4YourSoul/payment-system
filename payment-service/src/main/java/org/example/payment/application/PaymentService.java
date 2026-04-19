package org.example.payment.application;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.payment.api.dto.CreatePaymentRequest;
import org.example.payment.api.dto.PaymentDto;
import org.example.payment.application.mapper.PaymentEntityMapper;
import org.example.payment.domain.PaymentStatus;
import org.example.payment.infrastructure.persistence.PaymentEntity;
import org.example.payment.infrastructure.persistence.PaymentRepository;
import org.example.payment.infrastructure.persistence.UserRepository;
import org.example.payment.kafka.PaymentEventPublisher;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");

    private final PaymentEntityMapper mapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User not found id = " + request.userId());
        }

        if (request.amount().compareTo(MAX_AMOUNT) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount too large");
        }
        PaymentEntity payment = PaymentEntity.builder()
            .userId(request.userId())
            .amount(request.amount())
            .build();

        PaymentEntity saved = paymentRepository.saveAndFlush(payment);
        log.info("Payment created: ID: {}", saved.getId());

        eventPublisher.publishPaymentCreated(saved);
        return mapper.convertEntityToDto(saved);
    }

    @Cacheable(cacheNames = "payments", key = "#id")
    @Transactional(readOnly = true)
    public PaymentDto getPayment(UUID id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        return mapper.convertEntityToDto(payment);
    }

    @CacheEvict(cacheNames = "payments", key = "#id")
    @Transactional
    public PaymentDto confirmPayment(UUID id) {
        PaymentEntity payment = findPaymentOrThrow(id);

        if (PaymentStatus.NEW != payment.getStatus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment status must be NEW");
        }

        payment.setStatus(PaymentStatus.SUCCEEDED);

        PaymentEntity saved = paymentRepository.saveAndFlush(payment);
        log.info("Payment has been confirmed: ID: {}", id);

        eventPublisher.publishPaymentSucceeded(saved);
        return mapper.convertEntityToDto(saved);
    }

    private PaymentEntity findPaymentOrThrow(UUID id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Payment with id=" + id + " not found"));
    }
}
