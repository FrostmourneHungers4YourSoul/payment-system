CREATE TABLE IF NOT EXISTS audit.processed_events
(
    event_id     UUID PRIMARY KEY,
    processed_at TIMESTAMPTZ NOT NULL
);
