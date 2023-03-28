CREATE DATABASE covid_system;

USE covid_system;

CREATE TABLE user(
	id VARCHAR(6),
	nic VARCHAR(12),
	email_Or_phone VARCHAR(25) NOT NULL,
	password VARCHAR(15) NOT NULL,
	CONSTRAINT PRIMARY KEY(nic,id)
);

CREATE TABLE vaccine_card(
	nic VARCHAR(12),
	name VARCHAR(25),
	address VARCHAR(30),
	gender VARCHAR(7),
	age INT(3),
	contact VARCHAR(12),
	vaccine VARCHAR(10),
	dose INT(2),
	date DATE,
	district VARCHAR(20),
	moh_Area VARCHAR(20)
		
);
