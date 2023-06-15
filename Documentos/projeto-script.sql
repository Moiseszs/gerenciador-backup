CREATE DATABASE db_app;

USE db_app;

CREATE TABLE plano(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100),
    limite INT);
    
CREATE TABLE cliente(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200),
    data_nasc DATE);
    
 CREATE TABLE assinatura(
	id INT PRIMARY KEY AUTO_INCREMENT,
	plano_id INT,
    cliente_id INT,
    FOREIGN KEY(plano_id) REFERENCES plano(id),
    FOREIGN KEY(cliente_id) REFERENCES cliente(id));
    
    
    
CREATE TABLE computador(
	id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    descricao VARCHAR(200),
    ip VARCHAR(12),
    FOREIGN KEY(cliente_id) REFERENCES cliente(id));
    
CREATE TABLE back_up(
	id INT PRIMARY KEY AUTO_INCREMENT,
    computador_id INT NOT NULL,
    descricao VARCHAR(200),
    data_inicio DATE DEFAULT(CURRENT_DATE()),
    data_fim DATE NOT NULL,
    FOREIGN KEY (computador_id) REFERENCES computador(id)
);

INSERT INTO plano(nome, limite) VALUES ('Basico', 3), ('Medio', 5), ('Profissional', 10);


SELECT * FROM assinatura;

SELECT cliente.nome, plano.nome FROM assinatura, plano, cliente
WHERE assinatura.plano_id = plano.id
AND cliente.id = assinatura.cliente_id;

SELECT cliente.id, cliente.nome, cliente.data_nasc,
plano.nome
FROM cliente, assinatura, plano
WHERE cliente.id = assinatura.cliente_id
AND plano.id = assinatura.plano_id;

SELECT * FROM cliente;
SELECT * FROM computador;
SELECT * FROM back_up;

