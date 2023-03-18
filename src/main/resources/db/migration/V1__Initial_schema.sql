-- src/main/resources/db/migration/V1__Initial_schema.sql

CREATE TABLE location (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE merchant (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    location_id INTEGER REFERENCES location(id)
);

CREATE TABLE listing (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(20, 8) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    merchant_id INTEGER REFERENCES merchant(id)
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    rating INTEGER NOT NULL,
    comment TEXT NOT NULL,
    user_id INTEGER REFERENCES "user"(id),
    merchant_id INTEGER REFERENCES merchant(id)
);

CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(20, 8) NOT NULL,
    user_id INTEGER REFERENCES "user"(id),
    listing_id INTEGER REFERENCES listing(id)
);
