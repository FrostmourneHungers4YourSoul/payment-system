package org.example.payment.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.example.payment.domain.PaymentStatus;

public record PaymentDto(
    UUID id,
    Long userId,
    BigDecimal amount,
    PaymentStatus status,
    Instant createdAt,
    Instant updatedAt
) {

}
