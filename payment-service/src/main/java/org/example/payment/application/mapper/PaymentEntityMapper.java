package org.example.payment.application.mapper;

import org.example.payment.api.dto.PaymentDto;
import org.example.payment.infrastructure.persistence.PaymentEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentEntityMapper {

    public PaymentDto convertEntityToDto(PaymentEntity payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }

    public List<PaymentDto> mapListToDto(List<PaymentEntity> payments) {
        return payments.stream()
                .map(this::convertEntityToDto)
                .toList();
    }
}
