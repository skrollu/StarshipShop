INSERT INTO orders (order_number, order_date, sending_date, cancellation_date, return_date, price) VALUES
("123", GETDATE(), null, null, null, 100),
("456", GETDATE(), null, null, null, 100),
("789", GETDATE(), null, null, null, 100)
;

INSERT INTO order_line (sku_code, price, quantity, id_order) VALUES
("123", 100, 11, 1),
("456", 100, 2, 1)
;