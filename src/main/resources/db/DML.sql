-- DML
INSERT INTO user (first_name, last_name)
VALUES ('Jane', 'Doe');
VALUES ('John', 'Doe');

INSERT INTO account (account_number)
VALUES ('LT730000000000000000');
VALUES ('LT730000000000000001');
VALUES ('LT730000000000000002');

INSERT INTO balance (account_number, operation_date, beneficiary, comment, amount, currency, status)
VALUES ('LT730000000000000000','2022-06-01', null, null, 366.55, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-01', null, null, 261.62, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-01', null, null, 260.63, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-02', null, null, 221.66, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-02', null, null, 206.66, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-02', null, null, 193.44, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-03', null, null, 192.54, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-04', null, null, 212.54, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-05', null, null, 204.64, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-06', null, null, 190.38, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-06', null, null, 160.38, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-07', null, null, 116.61, 'EUR', null);
VALUES ('LT730000000000000000','2022-06-08', null, null, 110.66, 'EUR', null);

INSERT INTO transaction (account_number, operation_date, beneficiary, comment, amount, currency, status)
VALUES ('LT730000000000000000','2022-06-01', 'PAYPAL', null, 104.93, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-01', 'APPLE', null, 00.99, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-02', 'PAYPAL', null, 38.97, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-02', 'John Doe', null, 15, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-02', 'LIDL Cosmos', null, 13.22, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-03', ' ', null, 0.9, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-04', 'John Doe', null, 20, 'EUR', 'DEPOSIT');
VALUES ('LT730000000000000000','2022-06-05', 'UAB HELLO', null, 7.9, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-06', 'LIDL Cosmos', null, 14.26, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-06', 'John Doe', null, 30, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-07', 'LIDL Cosmos', null, 43.77, 'EUR', 'WITHDRAW');
VALUES ('LT730000000000000000','2022-06-08', 'LIDL Cosmos', null, 5.95, 'EUR', 'WITHDRAW');

INSERT INTO user_account (user_id, account_id)
VALUES (1, 1);
VALUES (1, 3);
VALUES (2, 2);

INSERT INTO balance_transaction (balance_id, transaction_id)
VALUES (2, 1);
VALUES (3, 2);
VALUES (4, 3);
VALUES (5, 4);
VALUES (6, 5);
VALUES (7, 6);
VALUES (8, 7);
VALUES (9, 8);
VALUES (10, 9);
VALUES (11, 10);
VALUES (12, 11);
VALUES (13, 12);


