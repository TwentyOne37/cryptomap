-- src/main/resources/db/migration/V2__Insert_sample_data.sql

-- Insert sample data for location
INSERT INTO locations (address, latitude, longitude)
VALUES ('123 Main St, New York, NY 10001', 40.712776, -74.005974),
       ('456 High St, Los Angeles, CA 90012', 34.052235, -118.243683),
       ('789 Market St, San Francisco, CA 94103', 37.7749, -122.419416),
       ('321 Union St, Seattle, WA 98101', 47.6062, -122.3321),
       ('654 Broadway St, Chicago, IL 60601', 41.8781, -87.6298);

-- Insert sample data for merchant
INSERT INTO merchants (name, email, location_id)
VALUES ('John Doe', 'john@example.com', 1),
       ('Jane Smith', 'jane@example.com', 2),
       ('Alice Brown', 'alice@example.com', 3),
       ('Bob Johnson', 'bob@example.com', 4),
       ('Charlie Williams', 'charlie@example.com', 5);

-- Insert sample data for listing
INSERT INTO listings (title, description, price, crypto, created_at, updated_at, merchant_id)
VALUES ('Sample Product', 'This is a sample product description', 100.00, 'BTC', NOW(), NOW(), 1),
       ('Tasty Street Food', 'Delicious street food from various cuisines', 50.00, 'ETH', NOW(), NOW(), 2),
       ('Handcrafted Jewelry', 'Beautiful handmade jewelry from local artisans', 200.00, 'BTC', NOW(), NOW(), 3),
       ('Vintage Clothing', 'Unique vintage clothing and accessories', 150.00, 'BTC', NOW(), NOW(), 4),
       ('Artisan Crafts', 'High-quality handmade crafts from local artists', 75.00, 'ETH', NOW(), NOW(), 5);

-- Insert sample data for user
INSERT INTO users (username, password, email)
VALUES ('johndoe', 'hashed_password_here', 'johndoe@example.com');

-- Insert sample data for review
INSERT INTO reviews (rating, comment, user_id, merchant_id)
VALUES (5, 'Great merchant, excellent service!', 1, 1);

-- Insert sample data for transaction
INSERT INTO transactions (amount, user_id, listing_id)
VALUES (100.00, 1, 1);
