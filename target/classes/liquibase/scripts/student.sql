-- liquibase formatted sql

-- changeset user:1

CREATE TABLE Task (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL
);
