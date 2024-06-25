--liquibase formatted sql

--changeset vladislav77777:1
CREATE TABLE recipes (
                         id BIGINT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         instructions TEXT NOT NULL,
                         ingredients TEXT NOT NULL,
                         image_url VARCHAR(255)
);
