--Definicao do banco de dados em SQL com base no modelo relacional.

CREATE SCHEMA pis;

CREATE TABLE pis.loja (
	nome VARCHAR(20),
	endereco VARCHAR(80),
	telefone VARCHAR(15),
	nome_logo VARCHAR(20),
	CONSTRAINT pk_loja PRIMARY KEY (nome)
);

CREATE TABLE pis.iphone(
    cod VARCHAR(15),
	loja_nome VARCHAR(20),
	link_iphone VARCHAR(100) NOT NULL,
	link_imagem VARCHAR(100) NOT NULL,
	titulo VARCHAR(30) NOT NULL,
	cor VARCHAR(15),
	preco_avista VARCHAR(15) NOT NULL,
	preco_aprazo VARCHAR(15) NOT NULL,
	tam_tela NUMERIC(2, 1),
	resolucao_cam_front VARCHAR(6),
	resolucao_cam_tras VARCHAR(6),
	mem_int VARCHAR(6),
	mem_ram VARCHAR(6),
	CONSTRAINT pk_iphone PRIMARY KEY(cod, loja_nome),
	CONSTRAINT fk_iphone FOREIGN KEY(loja_nome)
		REFERENCES pis.loja(nome) ON DELETE CASCADE
);

CREATE SEQUENCE pis.avaliacao_id_seq;
CREATE TABLE pis.avaliacao(
	id INT DEFAULT nextval('pis.avaliacao_id_seq'),
	titulo VARCHAR(30),
	descricao VARCHAR(150),
	data DATE,
	avaliador_nome VARCHAR(30),
	likes INT DEFAULT 0,
	deslikes INT DEFAULT 0,
	nota INT NOT NULL,
	iphone_cod VARCHAR(15) NOT NULL,
	loja_nome VARCHAR(20) NOT NULL,
	CONSTRAINT pk_avaliacao PRIMARY KEY(id),
	CONSTRAINT fk_avaliacao_iphone FOREIGN KEY(iphone_cod, loja_nome)
		REFERENCES pis.iphone(cod, loja_nome),
	CONSTRAINT ck_nota CHECK (nota >= 0 AND nota <= 5)
);

CREATE SEQUENCE pis.duvida_id_seq;
CREATE TABLE pis.duvida(
	id INT DEFAULT nextval('pis.duvida_id_seq'),
	titulo VARCHAR(30),
	descricao VARCHAR(150),
	data_duvida DATE,
	pessoa_nome VARCHAR(30),
	likes INT DEFAULT 0,
	deslikes INT DEFAULT 0,
	resposta VARCHAR(150),
	data_resposta DATE,
	iphone_cod VARCHAR(15) NOT NULL,
	loja_nome VARCHAR(20) NOT NULL,
	CONSTRAINT pk_duvida PRIMARY KEY(id),
	CONSTRAINT fk_duvida_iphone FOREIGN KEY(iphone_cod, loja_nome)
		REFERENCES pis.iphone(cod, loja_nome)
);