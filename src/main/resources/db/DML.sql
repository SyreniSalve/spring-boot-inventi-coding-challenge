-- DML
INSERT INTO transaction (operation_date, beneficiary, comment, amount, currency)
VALUES ('2022-06-01', 'PAYPAL', null, 104.93, 'EUR');
VALUES ('2022-06-01', 'APPLE', null, 00.99, 'EUR');
VALUES ('2022-06-02', 'PAYPAL', null, 38.97, 'EUR');
VALUES ('2022-06-02', 'John Doe', null, 15, 'EUR');
VALUES ('2022-06-02', 'LIDL Cosmos', null, 13.22, 'EUR');
VALUES ('2022-06-03', ' ', null, 0.9, 'EUR');
VALUES ('2022-06-02', 'John Doe', null, 20, 'EUR');
VALUES ('2022-06-04', 'UAB HELLO', null, 7.9, 'EUR');
VALUES ('2022-06-05', 'LIDL Cosmos', null, 14.26, 'EUR');
VALUES ('2022-06-06', 'John Doe', null, 30, 'EUR');
VALUES ('2022-06-06', 'LIDL Cosmos', null, 43.77, 'EUR');
VALUES ('2022-06-07', 'LIDL Cosmos', null, 5.95, 'EUR');


INSERT INTO account (account_number, balance)
VALUES ('LT730000000000000000', 336.55);

INSERT INTO user (first_name, last_name)
VALUES ('Jane', 'Doe');

INSERT INTO account_transaction (user_id, account_id, transaction_id)
VALUES (1, 1, 1);
VALUES (1, 1, 2);
VALUES (1, 1, 3);
VALUES (1, 1, 4);
VALUES (1, 1, 5);
VALUES (1, 1, 6);
VALUES (1, 1, 7);
VALUES (1, 1, 8);
VALUES (1, 1, 9);
VALUES (1, 1, 10);
VALUES (1, 1, 11);
VALUES (1, 1, 12);
