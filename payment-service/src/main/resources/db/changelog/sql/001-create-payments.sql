CREATE TABLE IF NOT EXISTS payments
(
    id         UUID PRIMARY KEY,
    user_id    BIGINT         NOT NULL,
    amount     NUMERIC(19, 2) NOT NULL,
    status     VARCHAR(32)    NOT NULL,
    created_at TIMESTAMPTZ    NOT NULL,
    updated_at TIMESTAMPTZ    NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_payments_user_id ON payments (user_id);
