--liquibase formatted sql

--changeset vladislav77777:3
ALTER TABLE recipes ALTER COLUMN ingredients TYPE VARCHAR(2048);

--changeset vladislav77777:4
ALTER TABLE recipes ALTER COLUMN instructions TYPE VARCHAR(8192);
