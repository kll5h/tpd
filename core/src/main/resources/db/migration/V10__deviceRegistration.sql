CREATE TABLE registered_device (
    id bigint auto_increment PRIMARY KEY,
    address varchar(36) NOT NULL,
    registration_token BLOB NOT NULL,
    name varchar(40) NOT NULL,
	IPAddress varchar(40) NOT NULL
);

CREATE TABLE device_data (
	id bigint auto_increment PRIMARY KEY,
    device_id bigint NOT NULL,
    data BLOB NOT NULL,
    date_time date,
    CONSTRAINT fk_device_id FOREIGN KEY (device_id) REFERENCES registered_device(id)
);