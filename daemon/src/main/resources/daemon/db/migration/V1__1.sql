CREATE TABLE block (
  id    BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  index BIGINT,
  hash  VARCHAR(255),
  time  BIGINT
);
ALTER TABLE block ADD CONSTRAINT index_hash_unique UNIQUE (index, hash);
CREATE INDEX block_index_idx ON block (index);
//CREATE INDEX block_hash_idx ON block (hash);
CREATE INDEX block_index_hash_idx ON block (index, hash);

CREATE TABLE asset (
  id        BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  asset_id  VARCHAR(255) UNIQUE,
  name      VARCHAR(255) UNIQUE NOT NULL,
  divisible BIT NOT NULL,
  booked     BIT NOT NULL
);

INSERT INTO asset VALUES (0, null, 'CNTRPRTY-TILECOINX', 1, 1);

CREATE INDEX asset_asset_id_idx ON asset (asset_id);
CREATE INDEX asset_name_idx ON asset (name);

CREATE TABLE message (
  id       BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  asset_id BIGINT NOT NULL,
  FOREIGN KEY (asset_id) REFERENCES asset (id)
);

CREATE TABLE issuance_status (
  id BIGINT NOT NULL PRIMARY KEY
);

INSERT INTO issuance_status VALUES (0);
INSERT INTO issuance_status VALUES (1);

CREATE TABLE issuance (
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  issuance_status_id BIGINT NOT NULL,
  CONSTRAINT fk_issuance_status FOREIGN KEY (issuance_status_id) REFERENCES issuance_status (id),
  CONSTRAINT fk_issuance_message FOREIGN KEY (id) REFERENCES message (id),
);

CREATE TABLE send (
  id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  CONSTRAINT fk_send_message FOREIGN KEY (id) REFERENCES message (id)
);

CREATE TABLE transaction (
  id          BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  hash        VARCHAR(255),
  source      VARCHAR(255),
  destination VARCHAR(255),
  btc_amount  BIGINT,
  fee         BIGINT,
  data        BLOB,
  block_id    BIGINT,
  message_id  BIGINT,
/*supported  BOOL DEFAULT 1,*/
  FOREIGN KEY (block_id) REFERENCES block (id),
  FOREIGN KEY (message_id) REFERENCES message (id)
);
ALTER TABLE transaction ADD CONSTRAINT transaction_hash_unique UNIQUE (hash);

CREATE INDEX transaction_hash_idx ON transaction (hash);


CREATE TABLE ledger_entry (
  id       BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  DTYPE    VARCHAR(255)          NOT NULL,
  address  VARCHAR(255),
  quantity BIGINT,
  transaction_id BIGINT,
  asset_id BIGINT,
/*action TEXT,
event TEXT,*/
  FOREIGN KEY (transaction_id) REFERENCES transaction (id),
  FOREIGN KEY (asset_id) REFERENCES asset (id)
);

CREATE INDEX address_idx ON ledger_entry (address);
//CREATE INDEX asset_idx ON debit (asset);

/*CREATE TABLE burn (
  id             BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  transaction_id BIGINT,
  source         VARCHAR(255),
  burned         BIGINT,
  earned         BIGINT,
  status         VARCHAR(255),
  FOREIGN KEY (transaction_id) REFERENCES transaction (id)
);

CREATE INDEX burn_status_idx ON burn (status);
CREATE INDEX burn_source_idx ON burn (source);*/

CREATE TABLE balance (
  id       BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  address  VARCHAR(255)          NOT NULL,
  asset_id BIGINT,
  quantity BIGINT,
  FOREIGN KEY (asset_id) REFERENCES asset (id)
);

ALTER TABLE balance ADD CONSTRAINT address_asset_id_unique UNIQUE (address, asset_id);
CREATE INDEX balance_address_idx ON balance (address);
