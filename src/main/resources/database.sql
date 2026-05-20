DROP DATABASE IF EXISTS auto_spare_parts_db;
CREATE DATABASE auto_spare_parts_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE auto_spare_parts_db;

-- 3NF notes:
-- users, categories, products, orders, and order_items each store facts about one entity.
-- order_items resolves the many-to-many relationship between orders and products.
-- category names are not duplicated in products; product data is not duplicated in orders except unit price snapshot.

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    password_hash VARCHAR(64) NOT NULL,
    password_salt VARCHAR(64) NOT NULL,
    role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    profile_photo VARCHAR(255),
    remember_token VARCHAR(128),
    remember_token_expiry DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(120) NOT NULL,
    brand VARCHAR(80) NOT NULL,
    part_number VARCHAR(80) NOT NULL UNIQUE,
    vehicle_type ENUM('Bike','Car','Scooter') NOT NULL DEFAULT 'Car',
    description TEXT,
    image_url VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT chk_products_price CHECK (price > 0),
    CONSTRAINT chk_products_stock CHECK (stock_quantity >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING','CONFIRMED','ON_PROCESS','SHIPPED','DELIVERED','CANCELLED') NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(255) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categories (name, description) VALUES
('Engine Parts', 'Filters, plugs, belts, pistons and engine maintenance items'),
('Brake System', 'Brake pads, rotors, calipers and brake accessories'),
('Electrical', 'Batteries, bulbs, sensors and wiring components'),
('Suspension', 'Shock absorbers, struts and control arms'),
('Body Parts', 'Mirrors, bumpers, handles and exterior fittings');

-- Default admin password: bars12345
INSERT INTO users (full_name, email, phone, address, password_hash, password_salt, role)
VALUES ('System Admin', 'bar2s@gmail.com', '9800000000', 'Admin Office',
'9bc37afccc24ce074800317deecc05c2d13b87112cef1d94b1cc1d979dd49cf9', 'autospares-admin-salt', 'ADMIN');

INSERT INTO products (category_id, name, brand, part_number, vehicle_type, description, price, stock_quantity) VALUES
(1, 'Premium Oil Filter', 'Bosch', 'BOS-OF-1001', 'Car', 'High-efficiency oil filter for petrol engines.', 1500.00, 45),
(2, 'Ceramic Brake Pads', 'Brembo', 'BRM-BP-2201', 'Car', 'Low-dust ceramic brake pad set for front wheels.', 5950.00, 28),
(3, '12V Car Battery', 'Exide', 'EXD-BAT-450', 'Car', 'Reliable 12V battery with strong cold start performance.', 12999.00, 12),
(4, 'Bike Chain Kit', 'Rolon', 'ROL-CHK-150', 'Bike', 'Durable chain and sprocket kit for daily motorcycles.', 3250.00, 30),
(2, 'Scooter Brake Shoe', 'TVS', 'TVS-BS-110', 'Scooter', 'Reliable rear brake shoe set for scooter braking.', 1150.00, 22),
(5, 'Side Mirror Assembly', 'Dorman', 'DOR-MIR-991', 'Car', 'Manual side mirror replacement assembly.', 3875.00, 16),
(4, 'Bike Shock Absorber', 'Monroe', 'MON-BIK-300', 'Bike', 'Rear shock absorber for smoother bike rides.', 7425.00, 20),
(1, 'Scooter Air Filter', 'Honda', 'HON-AF-125', 'Scooter', 'Air filter for scooter engine protection.', 850.00, 40);
