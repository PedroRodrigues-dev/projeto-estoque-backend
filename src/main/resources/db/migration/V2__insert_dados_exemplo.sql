INSERT INTO tipo_produto (nome, descricao) VALUES 
('Eletrônico', 'Dispositivos eletrônicos'),
('Eletrodoméstico', 'Aparelhos de uso doméstico'),
('Móvel', 'Móveis em geral');

INSERT INTO fornecedor (nome, cnpj, email, telefone) VALUES 
('Tech Distribuidora', '12.345.678/0001-99', 'contato@tech.com', '(11) 99999-0000'),
('Casa do Eletro', '98.765.432/0001-88', 'vendas@casaeletro.com', '(21) 88888-1111');

INSERT INTO produto (codigo, descricao, tipo_produto_id, fornecedor_id, valor_fornecedor, quantidade_estoque, ativo) VALUES 
('ELEC-001', 'Smartphone Galaxy X', 1, 1, 1200.00, 50, TRUE),
('ELETRO-001', 'Geladeira Frost Free', 2, 2, 1800.00, 30, TRUE),
('MOV-001', 'Cadeira Escritório', 3, 2, 300.00, 20, TRUE);

-- Admin Teste -> Senha: admin123
-- Usuário Comum -> Senha: user123 
INSERT INTO usuario (nome, email, senha_hash, papel) VALUES 
('Admin Teste', 'admin@admin.com', '$2a$10$i9e/iaLx7jbAae5YM4N9quZommhxSYVSTuZzjB/XobDe8ybBbrDv2', 'ADMIN'),
('Usuário Comum', 'user@user.com', '$2a$10$vltHxzuUKc/VN4HEAiTAn.WrcCT05Mh6kqndKu6MTPcnztBiTyjGa', 'COMUM');

INSERT INTO movimento_estoque (produto_id, tipo_movimentacao, valor_venda, quantidade_movimentada, usuario_responsavel_id, data_movimentacao) VALUES 
(1, 'ENTRADA', 1500.00, 10, 1, CURRENT_TIMESTAMP),
(2, 'ENTRADA', 2100.00, 5, 1, CURRENT_TIMESTAMP),
(3, 'ENTRADA', 500.00, 5, 1, CURRENT_TIMESTAMP);

INSERT INTO movimento_estoque (produto_id, tipo_movimentacao, valor_venda, quantidade_movimentada, usuario_responsavel_id, data_movimentacao) VALUES 
(1, 'SAIDA', 1500.00, 3, 2, CURRENT_TIMESTAMP),
(2, 'SAIDA', 2100.00, 2, 2, CURRENT_TIMESTAMP);