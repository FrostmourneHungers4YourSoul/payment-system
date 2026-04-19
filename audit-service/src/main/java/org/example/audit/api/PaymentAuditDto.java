package org.example.audit.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentAuditDto
    (
        UUID eventId,
        UUID paymentId,
        String eventType,
        Instant occurredAt,
        BigDecimal amount,
        UUID userId,
        String status,
        Instant createdAt
    ) {

}
