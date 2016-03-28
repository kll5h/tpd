CREATE TABLE asset_transaction (
	id bigint auto_increment PRIMARY KEY,
	account_id bigint NOT NULL,
	hash varchar(65) NOT NULL,
	amount decimal NOT NULL,
	currency varchar(50) NOT NULL,
	CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(id)
);