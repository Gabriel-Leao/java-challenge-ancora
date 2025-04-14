CREATE TABLE IF NOT EXISTS users (
     id         INT            AUTO_INCREMENT PRIMARY KEY,
     name       VARCHAR(100)   NOT NULL,
     email      VARCHAR(100)   NOT NULL,
     password   VARCHAR(100)   NOT NULL,
     created_at TIMESTAMP      NOT NULL       DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP      NOT NULL       DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_user_email UNIQUE (email)
);
