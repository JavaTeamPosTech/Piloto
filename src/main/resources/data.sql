CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE if not exists usuarios  (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    login VARCHAR(60) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT uk_usuario_login UNIQUE (login)
    );


CREATE TABLE IF NOT EXISTS enderecos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    complemento VARCHAR(255),
    cep VARCHAR(20) NOT NULL,
    usuario_id UUID,
    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS clientes (
    id UUID PRIMARY KEY NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero VARCHAR(30) NOT NULL,
    telefone VARCHAR(30) NOT NULL,
    metodo_pagamento_preferido VARCHAR(30) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_pedido DATE,
    saldo_pontos INTEGER,
    cliente_vip BOOLEAN NOT NULL,
    avaliacoes_feitas INTEGER,
    notificacoes_ativas BOOLEAN NOT NULL,
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT uk_cliente_cpf UNIQUE (cpf),
    CONSTRAINT uk_cliente_telefone UNIQUE (telefone)
    );

CREATE TABLE IF NOT EXISTS proprietarios (
    id UUID PRIMARY KEY NOT NULL,
    cnpj VARCHAR(20) NOT NULL,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255) NOT NULL,
    inscricao_estadual VARCHAR(50) NOT NULL,
    telefone_comercial VARCHAR(30) NOT NULL,
    whatsapp VARCHAR(30),
    status_conta VARCHAR(30) NOT NULL,
    CONSTRAINT uk_proprietario_cnpj UNIQUE (cnpj),
    CONSTRAINT uk_proprietario_razao_social UNIQUE (razao_social),
    CONSTRAINT uk_proprietario_inscricao_estadual UNIQUE (inscricao_estadual),
    CONSTRAINT fk_proprietario_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS generos (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(30) UNIQUE NOT NULL
    );


CREATE TABLE IF NOT EXISTS metodo_pagamento (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(30) UNIQUE NOT NULL
    );


CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(30) UNIQUE NOT NULL
    );


CREATE TABLE IF NOT EXISTS status_conta (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(30) UNIQUE NOT NULL
    );


INSERT INTO generos (descricao) VALUES
    ('MASCULINO'),
    ('FEMININO') ON CONFLICT (descricao) DO NOTHING;

INSERT INTO metodo_pagamento (descricao) VALUES
    ('DINHEIRO'),
    ('CREDITO'),
    ('DEBITO'),
    ('PIX') ON CONFLICT (descricao) DO NOTHING;

INSERT INTO roles (descricao) VALUES
    ('ADMIN'),
    ('PROPRIETARIO'),
    ('CLIENTE') ON CONFLICT (descricao) DO NOTHING;

INSERT INTO status_conta (descricao) VALUES
    ('ATIVO'),
    ('BLOQUEADO'),
    ('PENDENTE_APROVACAO'),
    ('PENDENTE_PAGAMENTO'),
    ('DESATIVADO') ON CONFLICT (descricao) DO NOTHING;



