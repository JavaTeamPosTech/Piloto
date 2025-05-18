CREATE TABLE if not exists usuarios  (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    email VARCHAR(40) UNIQUE,
    login VARCHAR(30),
    senha VARCHAR(30),
    ultima_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS enderecos (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(100),
    cidade VARCHAR(100),
    bairro VARCHAR(100),
    rua VARCHAR(255),
    numero INTEGER,
    complemento VARCHAR(255),
    cep VARCHAR(20),
    usuario_id BIGINT,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);



