CREATE TABLE contacts(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(256) NOT NULL,
    phone_number VARCHAR(11) NOT NULL
);