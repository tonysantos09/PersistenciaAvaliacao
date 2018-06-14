CREATE SCHEMA dbescola;
USE dbescola ;

CREATE TABLE aluno (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE escola (
  id INT(11) NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(45) NOT NULL,
  endereco VARCHAR(60) NOT NULL,
  datafundacao DATETIME NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE curso (
  id INT(11) NOT NULL AUTO_INCREMENT,
  idescola INT(11) NOT NULL,
  descricao VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_IDESCOLA_IDCURSO
    FOREIGN KEY (idescola)
    REFERENCES escola (id));

CREATE TABLE matricula (
  idcurso INT(11) NOT NULL,
  idaluno INT(11) NOT NULL,
  nota INT NULL,
  PRIMARY KEY (idcurso, idaluno),
  CONSTRAINT FK_IDALUNO
    FOREIGN KEY (idaluno)
    REFERENCES aluno (id),
  CONSTRAINT FK_IDCURSO
    FOREIGN KEY (idcurso)
    REFERENCES curso (id));
