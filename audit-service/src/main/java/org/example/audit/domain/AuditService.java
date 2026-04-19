package org.example.audit.domain;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.audit.api.PaymentAuditDto;
import org.example.audit.domain.db.PaymentAuditLogEntity;
import org.example.audit.domain.db.PaymentAuditLogRepository;
import org.example.audit.domain.db.ProcessedEventRepository;
import org.example.common.PaymentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final PaymentAuditLogRepository auditLogRepository;
    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    public void processEvent(PaymentEvent event) {
        boolean isEventAlreadyProcessed = checkIsEventProcessed(event);

        if (isEventAlreadyProcessed) {
            log.info("Event is already processed, skipped");
            return;
        }

        PaymentAuditLogEntity logEntity = convertEntityToLogEntity(event);
        auditLogRepository.save(logEntity);
        log.info("Stored audit log for payment: {}", event.paymentId());
    }

    private boolean checkIsEventProcessed(PaymentEvent event) {
        int insertedCount = processedEventRepository.insertIfNotExists(event.eventId());
        return insertedCount == 0;
    }

    @Transactional(readOnly = true)
    public List<PaymentAuditDto> getAuditsByPaymentId(UUID paymentId) {
        return auditLogRepository.findByPaymentId(paymentId).stream()
            .map(this::convertToPaymentAuditDto)
            .toList();
    }

    private PaymentAuditDto convertToPaymentAuditDto(PaymentAuditLogEntity paymentAuditLogEntity) {
        return new PaymentAuditDto(
            paymentAuditLogEntity.getEventId(),
            paymentAuditLogEntity.getPaymentId(),
            paymentAuditLogEntity.getEventType(),
            paymentAuditLogEntity.getOccurredAt(),
            paymentAuditLogEntity.getAmount(),
            paymentAuditLogEntity.getUserId(),
            paymentAuditLogEntity.getStatus(),
            paymentAuditLogEntity.getCreatedAt()
        );
    }

    private PaymentAuditLogEntity convertEntityToLogEntity(PaymentEvent event) {
        PaymentAuditLogEntity entity = new PaymentAuditLogEntity();
        entity.setEventId(event.eventId());
        entity.setPaymentId(event.paymentId());
        entity.setEventType(event.type().name());
        entity.setOccurredAt(event.occurredAt());
        entity.setAmount(event.amount());
        entity.setUserId(event.userId());
        entity.setStatus(event.status());
        return entity;
    }
}
