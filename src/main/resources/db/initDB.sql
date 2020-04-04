
DROP TABLE IF EXISTS "transaction";
DROP TABLE IF EXISTS "transaction_category";
DROP TABLE IF EXISTS "transaction_types";
DROP TABLE IF EXISTS "user_roles";
DROP TABLE IF EXISTS "users_currency";
DROP TABLE IF EXISTS "users";
DROP TABLE IF EXISTS "currency";

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE "currency" (
    "id"   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    "code"  varchar(3)      NOT NULL
);

CREATE TABLE "users"
(
    "id"            INTEGER             PRIMARY KEY      DEFAULT nextval('global_seq'),
    "email"         VARCHAR(100)        NOT NULL,
    "password"      VARCHAR(30)         NOT NULL,
    "registred"     TIMESTAMP           NOT NULL         DEFAULT 'NOW()',
    "enabled"       BOOLEAN             NOT NULL         DEFAULT 'TRUE',
    "id_currency"   INTEGER             NOT NULL,
    FOREIGN KEY     ("id_currency")     REFERENCES      "currency" ("id") ON DELETE CASCADE
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE "user_roles"
(
    "id_users"      INTEGER             NOT NULL,
    "roles"         VARCHAR(255)        NOT NULL,
    CONSTRAINT      "user_roles_idx"    UNIQUE          ("id_users", roles),
    FOREIGN KEY     ("id_users")        REFERENCES      "users" ("id")      ON DELETE CASCADE
);

CREATE TABLE "transaction_types"
(
    "id"    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    "code"  VARCHAR(10)    NOT NULL
);

CREATE TABLE "transaction_category" (
    "id"                        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    "name"                      VARCHAR(100)    NOT NULL,
    "id_users"                  INTEGER         NOT NULL,
    "id_transaction_types"      INTEGER         NOT NULL,
    FOREIGN KEY ("id_transaction_types")    REFERENCES "transaction_types"  ("id") ON DELETE CASCADE,
    FOREIGN KEY ("id_users")                REFERENCES "users"              ("id") ON DELETE CASCADE
);

CREATE TABLE "transaction"
(
    "id"                        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    "id_users"                  INTEGER         NOT NULL,
    "id_currency"               INTEGER         NOT NULL,
    "value"                     NUMERIC         NOT NULL,
    "id_transaction_category"   INTEGER         NOT NULL,
    "date"                      TIMESTAMP       NOT NULL DEFAULT 'NOW()',
    "name"                      VARCHAR(100)    NOT NULL,
    FOREIGN KEY ("id_users")                REFERENCES "users"                  ("id") ON DELETE CASCADE,
    FOREIGN KEY ("id_currency")             REFERENCES "currency"               ("id") ON DELETE CASCADE,
    FOREIGN KEY ("id_transaction_category") REFERENCES "transaction_category"   ("id") ON DELETE CASCADE
);

CREATE TABLE "users_currency"
(
    "id_users"         INTEGER             NOT NULL,
    "id_currency"      INTEGER             NOT NULL,
    CONSTRAINT      "users_currency_idx"    UNIQUE          ("id_users", "id_currency"),
    FOREIGN KEY     ("id_users")           REFERENCES      "users"      ("id")      ON DELETE CASCADE,
    FOREIGN KEY     ("id_currency")        REFERENCES      "currency"   ("id")      ON DELETE CASCADE
);


