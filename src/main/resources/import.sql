INSERT INTO roles (role_type) VALUES ('ADMINISTRATOR');
INSERT INTO roles (role_type) VALUES ('CUSTOMER');

INSERT INTO users (username, password, email, role_id) VALUES ('angel', '12345678', 'angel@example.com', (SELECT r.id FROM roles r WHERE r.role_type = 'ADMINISTRATOR'));




