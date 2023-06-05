USE db_app


SELECT computador.id, descricao, nome AS cliente FROM computador, cliente
WHERE computador.cliente_id = cliente.id
AND computador.descricao= 'Computador teste'

INSERT INTO plano(nome, limite) VALUES ('Iniciante', 3),
('Basico',5), ('Medio', 10), ('Avançado',20)

SELECT * FROM cliente