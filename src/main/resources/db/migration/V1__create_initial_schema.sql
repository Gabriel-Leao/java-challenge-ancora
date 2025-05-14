CREATE TABLE addresses (
    id            UUID         PRIMARY KEY,
    street        VARCHAR(150) NOT NULL,
    number        VARCHAR(20)  NOT NULL,
    complement    VARCHAR(100),
    city          VARCHAR(100) NOT NULL,
    state         CHAR(2)      NOT NULL,
    postal_code   VARCHAR(8)   NOT NULL
);

CREATE TABLE users (
    id          UUID         PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    email       VARCHAR(200) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    birthdate   DATE         NOT NULL,
    role        VARCHAR(10)  NOT NULL,
    address_id  UUID         REFERENCES  addresses(id) ON DELETE SET NULL,
    created_at  TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT UNIQUE_USER_EMAIL        UNIQUE(email),
    CONSTRAINT CHECK_ROLE               CHECK (role IN ('USER', 'ADMIN'))
);
