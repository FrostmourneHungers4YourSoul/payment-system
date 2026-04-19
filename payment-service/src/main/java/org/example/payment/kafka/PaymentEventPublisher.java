package org.example.payment.kafka;

import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.common.PaymentEvent;
import org.example.common.PaymentEventType;
import org.example.payment.infrastructure.persistence.PaymentEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private final String topic;

    public PaymentEventPublisher(
        KafkaTemplate<String, PaymentEvent> kafkaTemplate,
        @Value("${app.kafka.topics.payments}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishPaymentCreated(PaymentEntity payment) {
        publish(payment, PaymentEventType.PAYMENT_CREATED);
    }

    public void publishPaymentSucceeded(PaymentEntity payment) {
        publish(payment, PaymentEventType.PAYMENT_SUCCEEDED);
    }

    private void publish(PaymentEntity payment, PaymentEventType type) {
        PaymentEvent event = new PaymentEvent(
            UUID.randomUUID(),
            type,
            payment.getId(),
            Instant.now(),
            payment.getAmount(),
            payment.getUserId(),
            payment.getStatus().name()
        );

        kafkaTemplate.send(topic, payment.getId().toString(), event);
        log.info("Publish event {} for payment: {}", type, payment.getId());
    }
}
