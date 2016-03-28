CREATE TABLE currency (
	id bigint auto_increment PRIMARY KEY,
	name varchar(50) NOT NULL,
	CONSTRAINT unique_name UNIQUE (name)
);

CREATE TABLE balance (
	id bigint auto_increment PRIMARY KEY,
	amount decimal DEFAULT 0.0,
	currency_id bigint NOT NULL,
	address_id bigint NOT NULL,
	CONSTRAINT fk_currency_id FOREIGN KEY (currency_id) REFERENCES currency(id),
	CONSTRAINT fk_address_id FOREIGN KEY (address_id) REFERENCES address(id)
);