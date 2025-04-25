CREATE TABLE usuarios
(
    id bigint auto_increment primary key,
    nome             varchar(60) not null,
    email            varchar(40) unique,
    login            varchar(30),
    senha            varchar(30),
    ultima_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endereco         varchar(60)
);