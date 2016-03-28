CREATE TABLE asset_category (
	id bigint auto_increment PRIMARY KEY,
	name varchar(24) NOT NULL,
	parent_category_id bigint,
	CONSTRAINT fk_parent_category_id FOREIGN KEY (parent_category_id) REFERENCES asset_category(id)
);

INSERT INTO asset_category(name) VALUES('Currency');
INSERT INTO asset_category(name) VALUES('Stock');
INSERT INTO asset_category(name) VALUES('Bond');
INSERT INTO asset_category(name) VALUES('Commodity');
INSERT INTO asset_category(name) VALUES('Smart property');
INSERT INTO asset_category(name) VALUES('Points');
INSERT INTO asset_category(name) VALUES('Collectibles');
INSERT INTO asset_category(name) VALUES('Crypto-token');
INSERT INTO asset_category(name) VALUES('IoT');
INSERT INTO asset_category(name) VALUES('Email');
