
CREATE TABLE IF NOT EXISTS employees
  (
     id         BIGSERIAL PRIMARY KEY,
     email_id   VARCHAR(255) NOT NULL UNIQUE,
     first_name VARCHAR(255),
     last_name  VARCHAR(255)
  );