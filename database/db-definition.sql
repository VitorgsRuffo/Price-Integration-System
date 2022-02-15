CREATE SCHEMA pis;

CREATE SEQUENCE pis.store_id_seq;
CREATE TABLE pis.store (
	id INT DEFAULT nextval('pis.store_id_seq'),
	name VARCHAR(20) NOT NULL,
	logo_path VARCHAR(20),
	address VARCHAR(80),
	phone VARCHAR(15),
	CONSTRAINT pk_store PRIMARY KEY (id)
);

CREATE SEQUENCE pis.version_num_seq;
CREATE TABLE pis.script (
	store_id INT,
	version_num INT DEFAULT nextval('pis.version_num_seq'),
	date DATE,
	time TIME,
	text VARCHAR(2000),
	CONSTRAINT pk_script PRIMARY KEY (store_id, version_num),
	CONSTRAINT fk_script FOREIGN KEY (store_id)
		REFERENCES pis.store(id) ON DELETE CASCADE
);

CREATE TABLE pis.scriptExecution (
	store_id INT,
	script_version_num INT,
	date DATE,
	time TIME,
	CONSTRAINT pk_script_execution PRIMARY KEY(store_id, script_version_num, date, time),
	CONSTRAINT fk_script_execution FOREIGN KEY (store_id,script_version_num)
		REFERENCES pis.script(store_id,version_num) ON DELETE CASCADE
);

CREATE TABLE pis.iphone (
	model_name VARCHAR(20),
	sec_mem VARCHAR(20),
	color VARCHAR(30),
	title VARCHAR(80),
	iphone_link VARCHAR(200),
	image_link VARCHAR(200),
	model_cod VARCHAR(20),
	display_size VARCHAR(20),
	front_cam VARCHAR(20),
	back_cam VARCHAR(20),
	ram_mem VARCHAR(20),
	voltage VARCHAR(20),
	main_source VARCHAR(20),
	CONSTRAINT pk_iphone PRIMARY KEY(model_name, sec_mem, color)
);

CREATE SEQUENCE pis.iphoneVersion_id_seq;
CREATE TABLE pis.iphoneVersion (
	id INT DEFAULT nextval('pis.iphoneVersion_id_seq'),
	iphone_model_name VARCHAR(20),
	iphone_sec_mem VARCHAR(10),
	iphone_color VARCHAR(30),
	store_id INT,
	date date,
	cash_payment VARCHAR(30),
	installment_payment VARCHAR(40),
	rating_amount INT,
	rating_average NUMERIC,
	CONSTRAINT pk_iphoneVersion PRIMARY KEY(id, iphone_model_name, iphone_sec_mem, iphone_color, store_id),
	CONSTRAINT fk_iphoneVersion FOREIGN KEY (iphone_model_name, iphone_sec_mem, iphone_color)
		REFERENCES pis.iphone(model_name, sec_mem, color)
);

CREATE TABLE pis.rating (
	iphone_model_name VARCHAR(20),
	iphone_sec_mem VARCHAR(10),
	iphone_color VARCHAR(30),
	store_id INT,
	title VARCHAR(50),
	description VARCHAR(300),
	rater_name VARCHAR(35),
	rating NUMERIC,
	likes INT,
	deslikes INT,
	date DATE,
	CONSTRAINT pk_rating PRIMARY KEY(iphone_model_name, iphone_sec_mem, iphone_color, store_id, title, description),
	CONSTRAINT fk_rating FOREIGN KEY(iphone_model_name, iphone_sec_mem, iphone_color)
		REFERENCES pis.iphone(model_name, sec_mem, color)
);