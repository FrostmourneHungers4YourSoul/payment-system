package org.example.audit.api;

import java.util.List;

public record PaymentAuditHistoryDto(List<PaymentAuditDto> audits) {

}
