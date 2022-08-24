-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
--create sequence hibernate_sequence start with 1 increment by 1;

CREATE SCHEMA IF NOT EXISTS scheme_otus;

CREATE TABLE scheme_otus.address
(
    id     bigserial NOT NULL,
    street varchar(50),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE scheme_otus.client
(
    id         bigserial NOT NULL,
    name       varchar(50),
    address_id bigint    NOT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id),
    CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES scheme_otus.address (id) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE scheme_otus.phone
(
    id        bigserial NOT NULL,
    "number"  varchar(50),
    client_id bigint    NOT NULL,
    CONSTRAINT pk_phone PRIMARY KEY (id),
    CONSTRAINT fk_phone_client FOREIGN KEY (client_id) REFERENCES scheme_otus.client (id) ON DELETE CASCADE ON UPDATE RESTRICT
);
