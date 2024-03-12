INSERT INTO roles (role_type) VALUES ('ADMINISTRATOR');
INSERT INTO roles (role_type) VALUES ('CUSTOMER');

INSERT INTO users (username, password, email, role_id) VALUES ('angel', '12345678', 'angel@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('miguel', '12345678', 'miguel@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('andres', '12345678', 'andres@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'CUSTOMER'));
INSERT INTO users (username, password, email, role_id) VALUES ('laura', '12345678', 'laura@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('daniel', '12345678', 'daniel@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'CUSTOMER'));
INSERT INTO users (username, password, email, role_id) VALUES ('ana', '12345678', 'ana@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('carlos', '12345678', 'carlos@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'CUSTOMER'));
INSERT INTO users (username, password, email, role_id) VALUES ('luis', '12345678', 'luis@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('maria', '12345678', 'maria@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'CUSTOMER'));
INSERT INTO users (username, password, email, role_id) VALUES ('sara', '12345678', 'sara@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));
INSERT INTO users (username, password, email, role_id) VALUES ('jose', '12345678', 'jose@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'CUSTOMER'));

INSERT INTO Products (name, description, price, stock) VALUES ('Computador', 'Computador de última generación', 1500.00, 50);
INSERT INTO Products (name, description, price, stock) VALUES ('Celular', 'Teléfono inteligente de alta gama', 800.00, 100);
INSERT INTO Products (name, description, price, stock) VALUES ('Cámara', 'Cámara digital profesional', 600.00, 30);
INSERT INTO Products (name, description, price, stock) VALUES ('Tablet', 'Tableta con pantalla de alta resolución', 400.00, 80);
INSERT INTO Products (name, description, price, stock) VALUES ('Smartwatch', 'Reloj inteligente con monitor de actividad', 200.00, 60);
INSERT INTO Products (name, description, price, stock) VALUES ('Portátil', 'Portátil ligero y potente', 1200.00, 40);
INSERT INTO Products (name, description, price, stock) VALUES ('Impresora', 'Impresora multifuncional de alta velocidad', 300.00, 20);
INSERT INTO Products (name, description, price, stock) VALUES ('Altavoz Bluetooth', 'Altavoz portátil con conexión inalámbrica', 100.00, 150);
INSERT INTO Products (name, description, price, stock) VALUES ('Auriculares inalámbricos', 'Auriculares con tecnología Bluetooth', 80.00, 200);
INSERT INTO Products (name, description, price, stock) VALUES ('Monitor', 'Monitor de alta definición', 250.00, 50);

INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (5, 7500.00, (SELECT id FROM Products WHERE name = 'Computador'), 1500.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (2, 1600.00, (SELECT id FROM Products WHERE name = 'Celular'), 800.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (3, 1800.00, (SELECT id FROM Products WHERE name = 'Cámara'), 600.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (4, 1600.00, (SELECT id FROM Products WHERE name = 'Tablet'), 400.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (6, 1200.00, (SELECT id FROM Products WHERE name = 'Smartwatch'), 200.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (1, 1200.00, (SELECT id FROM Products WHERE name = 'Portátil'), 1200.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (2, 600.00, (SELECT id FROM Products WHERE name = 'Impresora'), 300.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (3, 300.00, (SELECT id FROM Products WHERE name = 'Altavoz Bluetooth'), 100.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (4, 320.00, (SELECT id FROM Products WHERE name = 'Auriculares inalámbricos'), 80.00);
INSERT INTO sale_details (quantity, subtotal, product_id, unit_price) VALUES (5, 1250.00, (SELECT id FROM Products WHERE name = 'Monitor'), 250.00);

INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 10:00:00', 'ACTIVO', (SELECT id FROM users WHERE username = 'angel'), 7500.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 10:15:00', 'COMPLETADO', (SELECT id FROM users WHERE username = 'miguel'), 1600.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 10:30:00', 'CANCELADO', (SELECT id FROM users WHERE username = 'andres'), 1800.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 10:45:00', 'ACTIVO', (SELECT id FROM users WHERE username = 'laura'), 1600.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 11:00:00', 'COMPLETADO', (SELECT id FROM users WHERE username = 'daniel'), 1200.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 11:15:00', 'CANCELADO', (SELECT id FROM users WHERE username = 'ana'), 1200.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 11:30:00', 'ACTIVO', (SELECT id FROM users WHERE username = 'carlos'), 600.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 11:45:00', 'COMPLETADO', (SELECT id FROM users WHERE username = 'luis'), 900.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 12:00:00', 'CANCELADO', (SELECT id FROM users WHERE username = 'maria'), 1200.00);
INSERT INTO sales (sale_date, sale_status, user_id, total) VALUES ('2024-03-11 12:15:00', 'ACTIVO', (SELECT id FROM users WHERE username = 'sara'), 1250.00);




