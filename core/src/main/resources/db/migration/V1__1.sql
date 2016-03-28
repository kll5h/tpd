CREATE TABLE country (
	id bigint PRIMARY KEY,
	name VARCHAR(255)
);

/* FIELDS SHOULD BE 255 LENGHT ON DATABASE LEVEL AND THEY SHOULD BE VALIDATE FROM WEB SIDE */
CREATE TABLE contact_details (
	id bigint auto_increment NOT NULL,
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	user_name VARCHAR(20) UNIQUE,
	phone VARCHAR(30),
	email VARCHAR(255),
	caption VARCHAR(140),
	address VARCHAR(255),
  	city VARCHAR(60),
	state VARCHAR(60),
	zip VARCHAR(30),
  	country_id bigint,
  	date_of_birth DATE,
	CONSTRAINT pk_contact_details PRIMARY KEY(id),
	CONSTRAINT fk_contact_details_country_id FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE wallet (
  id bigint auto_increment NOT NULL,
  CONSTRAINT pk_wallet PRIMARY KEY (id)
);

CREATE TABLE address (
  id bigint auto_increment NOT NULL,
  address varchar(255) NOT NULL,
  private_key varchar(255),
  wallet_id bigint,
  CONSTRAINT pk_address PRIMARY KEY (id),
  CONSTRAINT fk_address_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id)
);

CREATE TABLE account (
  id bigint auto_increment NOT NULL,
  pass_phrase varchar(255) NOT NULL,
  wallet_id bigint,
  CONSTRAINT pk_account PRIMARY KEY (id),
  CONSTRAINT fk_account_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id),
);

CREATE TABLE individual_account (
  id bigint NOT NULL,
  contact_details_id bigint,
  CONSTRAINT pk_individual_account PRIMARY KEY (id),
  CONSTRAINT fk_account1 FOREIGN KEY (id) REFERENCES account (id),
  CONSTRAINT fk_contact_details FOREIGN KEY (contact_details_id) REFERENCES contact_details (id)
);

CREATE TABLE company_contact_details (
  id bigint auto_increment NOT NULL PRIMARY KEY,
  company_name varchar(255),
  address varchar(255),
  zip varchar(30),
  city VARCHAR(60),
  state VARCHAR(60),
  country_id bigint,
  presence varchar(255),
  caption varchar(140),
  merchant_id varchar(255),
  company_type varchar(255),
  company_category varchar(255),
  tax_id_type varchar(255),
  tax_id varchar(30),
  first_name varchar(40),
  last_name varchar(40),
  email varchar(255),
  phone varchar(30),
  CONSTRAINT fk_company_contact_details_country_id FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE company_account (
  id bigint NOT NULL,
  company_contact_details_id bigint,
  CONSTRAINT pk_company_account PRIMARY KEY (id),
  CONSTRAINT fk_account2 FOREIGN KEY (id) REFERENCES account (id),
  CONSTRAINT fk_company_contact_details_id FOREIGN KEY (company_contact_details_id) REFERENCES company_contact_details(id)
);

