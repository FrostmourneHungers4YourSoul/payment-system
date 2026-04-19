package org.example.audit.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.audit.domain.AuditService;
import org.example.common.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentEventListener {

    private final AuditService auditService;

    public PaymentEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(
        topics = "${app.kafka.topics.payments}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(PaymentEvent event) {
        log.info("Received event: paymentId: {}, eventType: {}", event.paymentId(), event.type());
        auditService.processEvent(event);
    }
}
