CREATE TABLE IF NOT EXISTS stocks (
  id SERIAL PRIMARY KEY,
  product_id INT NOT NULL,
  quantity INT NOT NULL
);

INSERT INTO stocks (product_id, quantity) VALUES (1, 100);

ALTER SYSTEM SET max_prepared_transactions = 100;