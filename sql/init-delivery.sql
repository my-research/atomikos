CREATE TABLE IF NOT EXISTS deliveries (
  id INT AUTO_INCREMENT PRIMARY KEY,
  delivery_name VARCHAR(255) NOT NULL,
  delivery_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO deliveries (delivery_name) VALUES ('첫 번째 배송'), ('두 번째 배송');
