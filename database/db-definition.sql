CREATE SCHEMA pis;

CREATE SEQUENCE pis.store_id_seq;
CREATE TABLE pis.Stores (
	id INT DEFAULT nextval('pis.store_id_seq'),
	name VARCHAR(20) NOT NULL,
	logo_path VARCHAR(20),
	address VARCHAR(80),
	phone VARCHAR(15),
	CONSTRAINT pk_stores PRIMARY KEY (id)
);

CREATE SEQUENCE pis.version_num_seq;
CREATE TABLE pis.Scripts (
	store_id INT,
	version_num INT DEFAULT nextval('pis.version_num_seq'),
	date DATE,
	time TIME,
	text VARCHAR(10000),
	CONSTRAINT pk_scripts PRIMARY KEY (store_id, version_num),
	CONSTRAINT fk_scripts FOREIGN KEY (store_id)
		REFERENCES pis.Stores(id) ON DELETE CASCADE
);

CREATE TABLE pis.ScriptExecutions (
	store_id INT,
	script_version_num INT,
	date DATE,
	time TIME,
	CONSTRAINT pk_script_executions PRIMARY KEY(store_id, script_version_num, date, time),
	CONSTRAINT fk_script_executions FOREIGN KEY (store_id,script_version_num)
		REFERENCES pis.Scripts(store_id,version_num) ON DELETE CASCADE
);

CREATE TABLE pis.Iphones (
	model_name VARCHAR(50),
	sec_mem VARCHAR(50),
	color VARCHAR(30),
	title VARCHAR(400),
	image_link VARCHAR(400),
	model_cod VARCHAR(50),
	display_size VARCHAR(50),
	front_cam VARCHAR(50),
	back_cam VARCHAR(50),
	ram_mem VARCHAR(50),
	voltage VARCHAR(50),
	main_source VARCHAR(50),
	CONSTRAINT pk_iphones PRIMARY KEY(model_name, sec_mem, color)
);

CREATE SEQUENCE pis.iphoneVersion_id_seq;
CREATE TABLE pis.IphoneVersions (
	id INT DEFAULT nextval('pis.iphoneVersion_id_seq'),
	iphone_model_name VARCHAR(20),
	iphone_sec_mem VARCHAR(10),
	iphone_color VARCHAR(30),
	store_id INT,
	date date,
	cash_payment VARCHAR(30),
	installment_payment VARCHAR(500),
	rating_amount INT,
	rating_average NUMERIC,
	iphone_link VARCHAR(400),
	CONSTRAINT pk_iphone_versions PRIMARY KEY(id, iphone_model_name, iphone_sec_mem, iphone_color, store_id),
	CONSTRAINT fk_iphone_versions FOREIGN KEY (iphone_model_name, iphone_sec_mem, iphone_color)
		REFERENCES pis.Iphones(model_name, sec_mem, color),
	CONSTRAINT fk_iphone_versions_store FOREIGN KEY (store_id)
		REFERENCES pis.Stores(id)
);

CREATE TABLE pis.IphoneRatings (
	iphone_model_name VARCHAR(20),
	iphone_sec_mem VARCHAR(10),
	iphone_color VARCHAR(30),
	store_id INT,
	title VARCHAR(1000),
	description VARCHAR(3000),
	rater_name VARCHAR(35),
	rating NUMERIC,
	likes INT,
	deslikes INT,
	date DATE,
	CONSTRAINT pk_iphone_ratings PRIMARY KEY(iphone_model_name, iphone_sec_mem, iphone_color, store_id, title, description),
	CONSTRAINT fk_iphone_ratings FOREIGN KEY(iphone_model_name, iphone_sec_mem, iphone_color)
		REFERENCES pis.Iphones(model_name, sec_mem, color),
	CONSTRAINT fk_iphone_ratings_store FOREIGN KEY (store_id)
		REFERENCES pis.Stores(id)
);
