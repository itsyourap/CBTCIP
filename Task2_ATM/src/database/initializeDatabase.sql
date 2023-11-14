CREATE TABLE IF NOT EXISTS accounts
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id   TEXT    NOT NULL,
    user_name TEXT    NOT NULL,
    user_pin  INTEGER NOT NULL,
    balance  INTEGER NOT NULL
);


INSERT OR IGNORE INTO accounts (user_id, user_pin, user_name, balance)
VALUES ('test01', 1234, 'Test User 01', 800),
       ('test02', 5678, 'Test User 02', 1000);


CREATE TABLE IF NOT EXISTS transaction_type
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    transaction_type TEXT                              NOT NULL
);


INSERT OR IGNORE INTO transaction_type (transaction_type)
VALUES ('DEPOSIT'),
       ('WITHDRAW'),
       ('TRANSFER_DEPOSIT'),
       ('TRANSFER_WITHDRAW');

CREATE TABLE IF NOT EXISTS transactions
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id       INTEGER NOT NULL,
    transaction_type INTEGER NOT NULL,
    amount           INTEGER NOT NULL,
    other_party_account_id      INTEGER,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (transaction_type) REFERENCES transaction_type (id),
    FOREIGN KEY (other_party_account_id) REFERENCES accounts (id)
);

INSERT OR IGNORE INTO transactions (account_id, transaction_type, amount, other_party_account_id)
VALUES (1, 1, 500, NULL),
       (1, 2, 100, NULL),
       (1, 4, 200, 2),
       (2, 3, 200, 1),
       (2, 1, 1400, NULL),
       (2, 4, 600, 1),
       (1, 3, 600, 2)
