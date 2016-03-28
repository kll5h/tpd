ALTER TABLE asset_transaction DROP CONSTRAINT fk_account_id;
ALTER TABLE asset_transaction ALTER COLUMN account_id RENAME TO address_id;
ALTER TABLE asset_transaction ADD CONSTRAINT fk_transaction_address FOREIGN KEY (address_id) REFERENCES address(id);