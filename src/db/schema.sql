CREATE TABLE account
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

CREATE TABLE place
(
    user_session INT ,
    id   INT NOT NULL,
    row  INT NOT NULL,
    cell INT NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    price INT
);

CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    row        INT NOT NULL,
    cell       INT NOT NULL,
    account_id INT NOT NULL REFERENCES account (id),
    UNIQUE (session_id, row, cell)
);

INSERT INTO place(id, row, cell, price) values(11, 1, 1, 200);
INSERT INTO place(id, row, cell, price) values(12, 1, 2, 300);
INSERT INTO place(id, row, cell, price) values(13, 1, 3, 400);

INSERT INTO place(id, row, cell, price) values(21, 2, 1, 350);
INSERT INTO place(id, row, cell, price) values(22, 2, 2, 500);
INSERT INTO place(id, row, cell, price) values(23, 2, 3, 350);

INSERT INTO place(id, row, cell, price) values(31, 3, 1, 300);
INSERT INTO place(id, row, cell, price) values(32, 3, 2, 400);
INSERT INTO place(id, row, cell, price) values(33, 3, 3, 300);