CREATE TABLE tipo_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE fornecedor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18),
    email VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    tipo_produto_id BIGINT NOT NULL,
    fornecedor_id BIGINT,
    valor_fornecedor DECIMAL(15, 2),
    quantidade_estoque INT,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (tipo_produto_id) REFERENCES tipo_produto(id),
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id)
);

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL
);

CREATE TABLE movimento_estoque (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto_id BIGINT NOT NULL,
    tipo_movimentacao VARCHAR(10) NOT NULL,
    valor_venda DECIMAL(15, 2),
    quantidade_movimentada INT NOT NULL,
    data_movimentacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_responsavel_id BIGINT,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (usuario_responsavel_id) REFERENCES usuario(id)
);