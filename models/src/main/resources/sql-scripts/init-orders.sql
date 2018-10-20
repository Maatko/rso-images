INSERT INTO orders (submitted, customer_id, status) VALUES (TIMESTAMP '2017-03-01 11:23:38', 1, 'queued');
INSERT INTO orders (submitted, customer_id, status) VALUES (TIMESTAMP '2017-04-12 01:12:38', 1, 'queued');
INSERT INTO orders (submitted, customer_id, status) VALUES (TIMESTAMP '2017-06-17 12:00:08', 3, 'queued');

INSERT INTO orders_itemids (orders_id, itemids) VALUES (1, 123);
INSERT INTO orders_itemids (orders_id, itemids) VALUES (1, 323);
INSERT INTO orders_itemids (orders_id, itemids) VALUES (2, 12);