DELETE FROM user_roles;
DELETE FROM transaction;
DELETE FROM transaction_category;
DELETE FROM transaction_types;
DELETE FROM users;
DELETE FROM currency;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO currency (code) VALUES
('USD'),
('EUR'),
('PLN'),
('RUB');

INSERT INTO users (email, password, id_currency) VALUES
('user@user.ru', 'user', 100000),
('admin@admin.ru', 'admin', 100000);

INSERT INTO user_roles (roles, id_users) VALUES
('USER', 100004),
('ADMIN', 100005);

INSERT INTO transaction_types (code) VALUES
('INCOME'),
('EXPENSES');

INSERT INTO transaction_category (id_users, id_transaction_types, name) VALUES
(100004, 100006, 'Зарплата'),
(100004, 100006, 'Другое'),
(100004, 100007, 'Еда'),
(100004, 100007, 'Транспорт'),
(100004, 100007, 'Другое'),
(100005, 100006, 'Зарплата админ'),
(100005, 100006, 'Другое админ'),
(100005, 100007, 'Еда админ'),
(100005, 100007, 'Транспорт админ');

INSERT INTO transaction (id_users, id_transaction_category, id_currency, value, name) VALUES
(100004, 100008, 100000, 3000, 'Зарплата за месяц'),
(100004, 100009, 100000, 200, 'Продажа телефона'),
(100004, 100010, 100000, 100, 'Поход в магазин'),
(100004, 100011, 100000, 50, 'Заправка машины'),
(100004, 100012, 100000, 5, 'Покупка чайника'),
(100005, 100013, 100000, 3000, 'Зарплата за месяц админ'),
(100005, 100014, 100000, 200, 'Продажа телефона админ'),
(100005, 100015, 100000, 100, 'Поход в магазин админ'),
(100005, 100016, 100000, 50, 'Заправка машины админ');

INSERT INTO users_currency (id_users, id_currency) VALUES
(100004, 100000),
(100004, 100001),
(100004, 100002),
(100004, 100003),
(100005, 100000),
(100005, 100001);