INSERT INTO orders (order_number, id_user, order_date, sending_date, cancellation_date, return_date, price) VALUES
('123', '123', '2023-04-30', null, null, null, 100),
('456', '123', '2023-04-30', null, null, null, 100),
('789', '456', '2023-04-30', null, null, null, 100)
;

INSERT INTO order_line (sku_code, price, quantity, id_order) VALUES
('123', 100, 11, 1),
('456', 100, 2, 1)
;