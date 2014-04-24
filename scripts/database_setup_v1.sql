DROP TABLE FUNCIONARIO;
CREATE TABLE FUNCIONARIO (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        NOME VARCHAR(50),
        CPF VARCHAR(11),
        ENDERECO VARCHAR(50),
        IDADE INTEGER
    );
ALTER TABLE FUNCIONARIO ADD CONSTRAINT FUNCIONARIO_PRIMARY_KEY PRIMARY KEY (ID);

--ModRH
ALTER TABLE FUNCIONARIO ADD COLUMN DATA_NASCIMENTO DATE;
ALTER TABLE FUNCIONARIO ADD COLUMN DATA_ADMISSAO DATE;
ALTER TABLE FUNCIONARIO ADD COLUMN EMAIL VARCHAR(20);
ALTER TABLE FUNCIONARIO ADD COLUMN BANCO VARCHAR(20);
ALTER TABLE FUNCIONARIO ADD COLUMN AGENCIA INTEGER;
ALTER TABLE FUNCIONARIO ADD COLUMN CONTA INTEGER;

insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE) values ('Arnaldo da Silva', '11122233396', 'Al. Santos, 438', 40);
insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE) values ('Maria Pereira', '22233311169', 'Rua da Consolacao, 123', 35);
insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE) values ('Eduardo Andrade', '32060839823', 'Alameda Casa Branca', 29);
insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE) values ('Jos� Ant�nio', '44455566609', 'Rua um, 1', 25);


DROP TABLE AUDIT_LOG;
CREATE TABLE AUDIT_LOG (
        ID_LOG INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        LEVEL VARCHAR(10),
        MSG VARCHAR(100)
    );
ALTER TABLE AUDIT_LOG ADD CONSTRAINT AUDIT_LOG_PRIMARY_KEY PRIMARY KEY (ID_LOG);

select * from audit_log
select * from reembolso

select * from FUNCIONARIO
select * from funcionario where data_admissao between '2012-01-01' and '2014-12-31'
select distinct(banco) from funcionario



