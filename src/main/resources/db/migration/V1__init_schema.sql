
CREATE TABLE IF NOT EXISTS employees (
    emp_id         BIGSERIAL PRIMARY KEY,
    email_id       VARCHAR(255) NOT NULL UNIQUE,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    place          VARCHAR(100),
    employee_type  VARCHAR(50),
    internal_notes TEXT
);