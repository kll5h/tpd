ALTER TABLE currency ADD minersfee decimal NOT NULL AFTER name;

INSERT INTO currency VALUES (1, 'BTC', 0.0001);
INSERT INTO currency VALUES (2, 'TILECOINX', 0.00001);
INSERT INTO currency VALUES (3, 'SJCX', 0.0001);