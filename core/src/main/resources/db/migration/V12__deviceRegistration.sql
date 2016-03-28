ALTER TABLE registered_device ADD COLUMN wallet_id bigint;
ALTER TABLE registered_device ADD CONSTRAINT fk_wallet_id FOREIGN KEY (wallet_id) REFERENCES wallet(id);