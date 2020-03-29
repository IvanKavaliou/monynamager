
DROP TABLE IF EXISTS "transaction";
DROP TABLE IF EXISTS "transaction_category";
DROP TABLE IF EXISTS "transaction_types";
DROP TABLE IF EXISTS "user_roles";
DROP TABLE IF EXISTS "users";
DROP TABLE IF EXISTS "currency";

CREATE TABLE "currency" (
    "id"            serial          NOT NULL,
    "name"          varchar(255)    NOT NULL,
    "code"          varchar(3)      NOT NULL,
    CONSTRAINT "Currency_pk" PRIMARY KEY ("idCurrency")
);

CREATE TABLE "users"
(
    "id"         serial         NOT NULL,
    "email"      varchar(100)   NOT NULL,
    "password"   varchar(30)    NOT NULL,
    "registred"  TIMESTAMP      NOT NULL DEFAULT 'NOW()',
    "enabled"    BOOLEAN        NOT NULL DEFAULT 'TRUE',
    "id_currency" integer       NOT NULL,
    CONSTRAINT "Users_pk" PRIMARY KEY ("id"),
    FOREIGN KEY ("id_currency") REFERENCES "currency" ("id_currency") ON DELETE CASCADE
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE "user_roles"
(
    "id_users"   integer         NOT NULL,
    "role"      varchar(255)    NOT NULL,
    CONSTRAINT "UserRolesIdx" UNIQUE ("id_users", role),
    FOREIGN KEY ("id_users") REFERENCES "users" ("id") ON DELETE CASCADE
);

CREATE TABLE "transaction_types"
(
    "id"                    serial          NOT NULL,
    "name"                  varchar(255)    NOT NULL,
    CONSTRAINT "TransactionTypes_pk" PRIMARY KEY ("id")
);

CREATE TABLE "transaction_category" (
    "id"                        serial          NOT NULL,
    "name"                      varchar(255)    NOT NULL,
    "idUser"                    integer         NOT NULL,
    "id_transaction_types"      integer         NOT NULL,
    CONSTRAINT "TransactionCategory_pk" PRIMARY KEY ("id"),
    FOREIGN KEY ("id_transaction_types") REFERENCES "transaction_types" ("id") ON DELETE CASCADE
);

CREATE TABLE "transaction"
(
    "id"                        serial      NOT NULL,
    "id_users"                  integer     NOT NULL,
    "id_transaction_types"      integer     NOT NULL,
    "id_currency"               integer     NOT NULL,
    "value"                     numeric     NOT NULL,
    "id_transaction_category"   bigint      NOT NULL,
    "date"                      TIMESTAMP   NOT NULL DEFAULT 'NOW()',
    CONSTRAINT "Transaction_pk" PRIMARY KEY ("id"),
    FOREIGN KEY ("id_users") REFERENCES "users" ("id_users") ON DELETE CASCADE,
    FOREIGN KEY ("id_transaction_types") REFERENCES "transaction_types" ("id_transaction_types") ON DELETE CASCADE,
    FOREIGN KEY ("id_currency") REFERENCES "currency" ("id_currency") ON DELETE CASCADE,
    FOREIGN KEY ("id_transaction_category") REFERENCES "transaction_category" ("id_transaction_category") ON DELETE CASCADE
);



