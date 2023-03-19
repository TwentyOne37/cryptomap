-- src/main/resources/db/migration/V2__Insert_sample_data.sql

-- Insert sample data for location
INSERT INTO location (address, latitude, longitude)
VALUES ('123 Main St, New York, NY 10001', 40.712776, -74.005974);

-- Insert sample data for merchant
INSERT INTO merchant (name, email, location_id)
VALUES ('John Doe', 'john@example.com', 1);

-- Insert sample data for listing
INSERT INTO listing (title, description, price, currency, merchant_id)
VALUES ('Sample Product', 'This is a sample product description', 100.00, 'USD', 1);

-- Insert sample data for user
INSERT INTO "user" (username, password, email)
VALUES ('johndoe', 'hashed_password_here', 'johndoe@example.com');

-- Insert sample data for review
INSERT INTO review (rating, comment, user_id, merchant_id)
VALUES (5, 'Great merchant, excellent service!', 1, 1);

-- Insert sample data for transaction
INSERT INTO transaction (amount, user_id, listing_id)
VALUES (100.00, 1, 1);
