CREATE TABLE if not exists usuarios  (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    login VARCHAR(60) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    ultima_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_descricao VARCHAR(30) NOT NULL,
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT uk_usuario_login UNIQUE (login)
);


CREATE TABLE IF NOT EXISTS enderecos (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    complemento VARCHAR(255),
    cep VARCHAR(20) NOT NULL,
    usuario_id INTEGER,
    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY,
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
    id INTEGER PRIMARY KEY NOT NULL,
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

-- CREATE TABLE IF NOT EXISTS alergia_alimentar (
--     id SERIAL PRIMARY KEY,
--     descricao VARCHAR(50) UNIQUE NOT NULL
-- );
--
-- INSERT INTO alergia_alimentar (descricao) VALUES
--     ('LACTOSE'),
--     ('GLUTEN'),
--     ('OVOS'),
--     ('AMENDOIM'),
--     ('CASTANHAS'),
--     ('NOZES'),
--     ('MARISCOS'),
--     ('FRUTOS_DO_MAR'),
--     ('PEIXE'),
--     ('SOJA'),
--     ('TRIGO'),
--     ('MILHO'),
--     ('GERGELIM'),
--     ('FRUTAS_CITRICAS'),
--     ('CHOCOLATE');
--
-- CREATE TABLE IF NOT EXISTS cliente_alergias (
--     id SERIAL PRIMARY KEY,
--     cliente_id INTEGER NOT NULL,
--     alergia_descricao VARCHAR(50) NOT NULL,
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (alergia_descricao) REFERENCES alergia_alimentar(descricao) ON DELETE CASCADE
--     );


CREATE TABLE IF NOT EXISTS generos (
     id SERIAL PRIMARY KEY,
     descricao VARCHAR(30) UNIQUE NOT NULL
    );

-- TESTANDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
-- CREATE TABLE IF NOT EXISTS cliente_genero (
--     cliente_id INTEGER NOT NULL,
--     genero_id INTEGER NOT NULL,
--     PRIMARY KEY (cliente_id, genero_id),
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (genero_id) REFERENCES generos(id)
-- );
--
-- INSERT INTO generos (descricao) VALUES
--   ('MASCULINO'),
--   ('FEMININO');


CREATE TABLE IF NOT EXISTS metodo_pagamento (
       id SERIAL PRIMARY KEY,
       descricao VARCHAR(30) UNIQUE NOT NULL
    );

-- TESTANDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
-- CREATE TABLE IF NOT EXISTS cliente_metodo_pagamento (
--     cliente_id INTEGER NOT NULL,
--     metodo_id INTEGER NOT NULL,
--     PRIMARY KEY (cliente_id, metodo_id),
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (metodo_id) REFERENCES metodo_pagamento(id)
--     );

-- INSERT INTO metodo_pagamento (descricao) VALUES
-- ('DINHEIRO'),
-- ('CREDITO'),
-- ('DEBITO'),
-- ('PIX');


CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(30) UNIQUE NOT NULL
    );

-- TESTANDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
-- CREATE TABLE IF NOT EXISTS cliente_role (
--     cliente_id INTEGER NOT NULL,
--     role_id INTEGER NOT NULL,
--     PRIMARY KEY (cliente_id, role_id),
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (role_id) REFERENCES roles(id)
--     );

-- INSERT INTO roles (descricao) VALUES
--      ('ADMIN'),
--      ('PROPRIETARIO'),
--      ('CLIENTE');


CREATE TABLE IF NOT EXISTS status_conta (
     id SERIAL PRIMARY KEY,
     descricao VARCHAR(30) UNIQUE NOT NULL
    );

-- TESTANDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
-- CREATE TABLE IF NOT EXISTS cliente_status_conta (
--     cliente_id INTEGER NOT NULL,
--     status_conta_id INTEGER NOT NULL,
--     PRIMARY KEY (cliente_id, status_conta_id),
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (status_conta_id) REFERENCES status_conta(id)
--     );
--
-- INSERT INTO status_conta (descricao) VALUES
--     ('ATIVO'),
--     ('BLOQUEADO'),
--     ('PENDENTE_APROVACAO'),
--     ('PENDENTE_PAGAMENTO'),
--     ('DESATIVADO');


-- CREATE TABLE IF NOT EXISTS preferencia_alimentar (
--     id SERIAL PRIMARY KEY,
--     descricao VARCHAR(30) UNIQUE NOT NULL
--     );
--
-- CREATE TABLE IF NOT EXISTS cliente_preferencias_alimentares (
--     cliente_id INTEGER NOT NULL,
--     preferencia_alimentar_descricao INTEGER NOT NULL,
--     PRIMARY KEY (cliente_id, preferencia_alimentar_descricao),
--     FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
--     FOREIGN KEY (preferencia_alimentar_descricao) REFERENCES preferencia_alimentar(id)
--     );
--
-- INSERT INTO preferencia_alimentar (descricao) VALUES
--   ('CHURRASCO'),
--   ('ITALIANA'),
--   ('BRASILEIRA'),
--   ('MASSAS'),
--   ('JAPONESA'),
--   ('VEGETARIANA'),
--   ('VEGANA'),
--   ('ARABE'),
--   ('CHINESA'),
--   ('MEXICANA'),
--   ('INDIANA'),
--   ('FRANCESA'),
--   ('FAST_FOOD'),
--   ('DOCES'),
--   ('FRUTOS_DO_MAR'),
--   ('PIZZARIA'),
--   ('LANCHES'),
--   ('SAUDAVEL');



