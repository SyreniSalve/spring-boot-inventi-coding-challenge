-- DDL
CREATE TABLE user
(
    `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
);

CREATE TABLE account
(
    `id`             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `account_number` varchar(20)        NOT NULL UNIQUE,

);

CREATE TABLE balance
(
    `id`         bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `date`       date               NOT NULL,
    `account_id` bigint             NOT NULL,
    FOREIGN KEY (`account_id`) REFERENCES account (`id`),


);

CREATE TABLE transaction
(
    `account_number` varchar(20)        NOT NULL,
    `id`             bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `operation_date` date               NOT NULL,
    `beneficiary`    varchar(255)       NOT NULL,
    `comment`        varchar(255) NULL,
    `status`         varchar(20)        NOT NULL,
    `amount`         double             NOT NULL,
    `currency`       varchar(120)       NOT NULL,
--     `user_id`        bigint             NOT NULL,
--     `account_id`     bigint             NOT NULL,
--     FOREIGN KEY (`user_id`) REFERENCES user (`id`),
--     FOREIGN KEY (`account_id`) REFERENCES account (`id`),
);

CREATE TABLE `user_account`
(
    `user_id`    bigint NOT NULL,
    `account_id` bigint NOT NULL,
    UNIQUE (user_id, account_id),
    FOREIGN KEY (`user_id`) REFERENCES user (`id`),
    FOREIGN KEY (`account_id`) REFERENCES account (`id`)
);

CREATE TABLE `balance_transaction`
(
    `balance_id`     bigint NOT NULL,
    `transaction_id` bigint NOT NULL,
    UNIQUE (balance_id, transaction_id),
    FOREIGN KEY (`balance_id`) REFERENCES balance (`id`),
    FOREIGN KEY (`transaction_id`) REFERENCES transaction (`id`)
);

