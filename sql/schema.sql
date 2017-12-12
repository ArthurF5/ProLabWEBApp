CREATE DATABASE IF NOT EXISTS prolabDB;
USE prolabDB;

CREATE TABLE IF NOT EXISTS users(
	usuarioID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	usuario VARCHAR(15) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    permissao INT NOT NULL
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS laboratorio(
	labID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    laboratorio VARCHAR(50) NOT NULL
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS UF(
	ufID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nome VARCHAR(75) NOT NULL,
    uf VARCHAR(2) NOT NULL
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS cidade(
	cidadeID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    ufID INT NOT NULL,
    CONSTRAINT cidadeUF FOREIGN KEY(ufID) REFERENCES UF(ufID)
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS endereco(
	endID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    labID INT NOT NULL,
    rua VARCHAR(50) NOT NULL,
    numero VARCHAR(4) NOT NULL,
    cidadeID INT NOT NULL,
    ufID INT NOT NULL,
    CONSTRAINT enderecoCidade FOREIGN KEY(cidadeID) REFERENCES cidade(cidadeID),
    CONSTRAINT enderecoUF FOREIGN KEY(ufID) REFERENCES UF(ufID),
    CONSTRAINT enderecoLaboratorio FOREIGN KEY(labID) REFERENCES laboratorio(labID)
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS exame(
	exameID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    exame VARCHAR(50) NOT NULL
)ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS exameLaboratorio(
	exameLabID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    labID INT NOT NULL,
    exameID INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    CONSTRAINT laboratorioID FOREIGN KEY(labID) REFERENCES laboratorio(labID),
    CONSTRAINT exameID FOREIGN KEY(exameID) REFERENCES exame(exameID)
)ENGINE = INNODB;