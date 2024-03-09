INSERT INTO roles (role_type) VALUES ('ADMINISTRATOR');
INSERT INTO roles (role_type) VALUES ('CUSTOMER');

INSERT INTO users (username, password, email, role_id) VALUES ('angel', '12345678', 'angel@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));

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




