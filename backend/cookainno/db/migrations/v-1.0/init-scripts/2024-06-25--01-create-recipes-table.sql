--liquibase formatted sql

--changeset vladislav77777:1
DROP TABLE IF EXISTS recipes;

CREATE TABLE recipes (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         instructions TEXT NOT NULL,
                         ingredients TEXT NOT NULL,
                         image_url VARCHAR(255)
);
