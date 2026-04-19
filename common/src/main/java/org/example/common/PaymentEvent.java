package org.example.common;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentEvent(
    UUID eventId,
    PaymentEventType type,
    UUID paymentId,
    Instant occurredAt,
    BigDecimal amount,
    UUID userId,
    String status
) {

}
