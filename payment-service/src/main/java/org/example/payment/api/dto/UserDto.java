package org.example.payment.api.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        List<PaymentDto> payments
) {
}
