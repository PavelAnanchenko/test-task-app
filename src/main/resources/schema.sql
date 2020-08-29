DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS errors;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name          VARCHAR                             NOT NULL,
  email         VARCHAR                             NOT NULL,
  age           INTEGER                             NOT NULL,
  created       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE errors (
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  message       VARCHAR                             NOT NULL,
  created       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
