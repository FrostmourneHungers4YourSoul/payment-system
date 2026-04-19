CREATE TABLE IF NOT EXISTS audit.payment_audit_logs
(
    event_id    UUID PRIMARY KEY,
    payment_id  UUID           NOT NULL,
    event_type  VARCHAR(64)    NOT NULL,
    occurred_at TIMESTAMPTZ    NOT NULL,
    amount      NUMERIC(19, 2) NOT NULL,
    user_id     UUID           NOT NULL,
    status      VARCHAR(32)    NOT NULL,
    created_at  TIMESTAMPTZ    NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_payment_audit_logs_payment_id
    ON audit.payment_audit_logs (payment_id);
