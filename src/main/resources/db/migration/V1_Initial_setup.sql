CREATE SEQUENCE flower_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE users_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE order_item_seq START 1 INCREMENT 1;
CREATE SEQUENCE orders_seq START 1 INCREMENT 1;

-- Updated table: flower
CREATE TABLE IF NOT EXISTS flower (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flower_name VARCHAR(255),
    category VARCHAR(255),
    color VARCHAR(50),
    origin_country VARCHAR(255),
    description TEXT,
    image_filename VARCHAR(255),
    price INT,
    availability BOOLEAN,
    flower_rating DOUBLE
);

-- Order Item Table: holds individual order item records
CREATE TABLE order_item (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    amount BIGINT,
    quantity BIGINT,
    flower_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_item_flower FOREIGN KEY (flower_id) REFERENCES flower(id)
);

-- Orders Table: holds order-level details for each customer order
CREATE TABLE orders (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    address VARCHAR(255),
    city VARCHAR(255),
    order_date DATE,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    post_index INT,
    total_price FLOAT8,
    PRIMARY KEY (id)
);

-- Mapping table between orders and order_items (Many-to-Many relationship)
CREATE TABLE orders_order_items (
    order_id BIGINT NOT NULL,
    order_items_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, order_items_id),
    CONSTRAINT fk_orders_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_orders_order_items_order_item FOREIGN KEY (order_items_id) REFERENCES order_item(id)
);

-- Review Table: holds customer reviews
CREATE TABLE review (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    author VARCHAR(255),
    review_date DATE,
    message VARCHAR(255),
    rating INT,
    PRIMARY KEY (id)
);

-- Mapping table for flower reviews (if a single flower can have multiple reviews)
CREATE TABLE flower_reviews (
    flower_id BIGINT NOT NULL,
    reviews_id BIGINT NOT NULL,
    PRIMARY KEY (flower_id, reviews_id),
    CONSTRAINT fk_flower_reviews_flower FOREIGN KEY (flower_id) REFERENCES flower(id),
    CONSTRAINT fk_flower_reviews_review FOREIGN KEY (reviews_id) REFERENCES review(id)
);

-- User Role Table: associates users with roles
CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    roles VARCHAR(255),
    PRIMARY KEY (user_id, roles)
);

-- Users Table: holds user account details
CREATE TABLE users (
    id BIGINT NOT NULL,
    activation_code VARCHAR(255),
    active BOOLEAN NOT NULL,
    address VARCHAR(255),
    city VARCHAR(255),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    password_reset_code VARCHAR(255),
    phone_number VARCHAR(255),
    post_index VARCHAR(255),
    provider VARCHAR(255),
    PRIMARY KEY (id)
);

-- Foreign key constraint for user_role referencing users
ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES users(id);