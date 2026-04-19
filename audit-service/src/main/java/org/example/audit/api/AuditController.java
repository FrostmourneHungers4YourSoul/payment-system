package org.example.audit.api;

import java.util.UUID;
import org.example.audit.domain.AuditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/payments/{paymentId}")
    public PaymentAuditHistoryDto getPaymentAudit(@PathVariable UUID paymentId) {
        return new PaymentAuditHistoryDto(auditService.getAuditsByPaymentId(paymentId));
    }
}
