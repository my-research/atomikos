CREATE TABLE IF NOT EXISTS orders (
  id SERIAL PRIMARY KEY,
  order_name VARCHAR(255) NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO orders (order_name) VALUES ('첫 주문'), ('두 번째 주문');
