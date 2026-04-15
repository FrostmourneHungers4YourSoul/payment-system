package org.example.common;

import java.math.BigDecimal;
import java.util.UUID;


public record Payment(
    UUID id,
    BigDecimal amount,
    PaymentStatus status
) {
}
