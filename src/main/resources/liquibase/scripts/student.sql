-- liquibase formatted sql

-- changeset user:1

CREATE TABLE notification_task (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    data TIMESTAMP
);
