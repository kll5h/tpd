CREATE TABLE device (
  id    BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  jwt BLOB NOT NULL,
  ip_address varchar(20)
);