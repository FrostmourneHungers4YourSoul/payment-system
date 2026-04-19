package org.example.audit.domain.db;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentAuditLogRepository extends JpaRepository<PaymentAuditLogEntity, UUID> {

    List<PaymentAuditLogEntity> findByPaymentId(UUID paymentId);
}
