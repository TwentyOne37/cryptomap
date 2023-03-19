-- src/main/resources/db/migration/V1__Initial_schema.sql

CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE merchants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    location_id INTEGER REFERENCES locations(id)
);

CREATE TABLE listings (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(20, 8) NOT NULL,
    crypto VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    merchant_id INTEGER REFERENCES merchants(id)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    rating INTEGER NOT NULL,
    comment TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id),
    merchant_id INTEGER REFERENCES merchants(id)
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(20, 8) NOT NULL,
    user_id INTEGER REFERENCES users(id),
    listing_id INTEGER REFERENCES listings(id)
);

