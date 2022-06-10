-- DDL
CREATE TABLE user
(
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
);

CREATE TABLE account
(
    `id`             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `account_number` varchar(20)        NOT NULL UNIQUE,
    `balance`        double             NOT NULL,
);

CREATE TABLE transaction
(
    `id`             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `operation_date` date               NOT NULL,
    `beneficiary`    varchar(255)       NOT NULL,
    `comment`        varchar(255) NULL,
    `amount`         double             NOT NULL,
    `currency`       varchar(120)       NOT NULL,
);

CREATE TABLE `account_transaction`
(
    `user_id`        bigint NOT NULL,
    `account_id`     bigint NOT NULL,
    `transaction_id` bigint NOT NULL,
    UNIQUE (`transaction_id`),
    FOREIGN KEY (`user_id`) REFERENCES user (`id`),
    FOREIGN KEY (`account_id`) REFERENCES account (`id`)
    FOREIGN KEY (`transaction_id`) REFERENCES transaction (`id`)
);
