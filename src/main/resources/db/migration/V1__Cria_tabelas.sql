
CREATE TABLE ponto_coleta (
     id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     nome VARCHAR2(255) NOT NULL,
     endereco VARCHAR2(500) NOT NULL,
     tipo_residuos VARCHAR2(255) NOT NULL,
     capacidade_maxima NUMBER(10,2) NOT NULL,
     nivel_atual NUMBER(10,2) DEFAULT 0 NOT NULL,
     ativo NUMBER(1) DEFAULT 1 NOT NULL,
     data_cadastro TIMESTAMP NOT NULL,
     ultima_atualizacao TIMESTAMP NOT NULL
);


CREATE TABLE descarte (
      id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      ponto_coleta_id NUMBER NOT NULL,
      ipo_residuo VARCHAR2(255) NOT NULL,
      quantidade NUMBER(10,2) NOT NULL,
      usuario_responsavel VARCHAR2(100) NOT NULL,
      data_descarte TIMESTAMP NOT NULL,
      observacoes VARCHAR2(500),
      CONSTRAINT fk_descarte_ponto_coleta FOREIGN KEY (ponto_coleta_id)
      REFERENCES ponto_coleta(id)
);


CREATE TABLE alerta (
      id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      ponto_coleta_id NUMBER NOT NULL,
      tipo_alerta VARCHAR2(50) NOT NULL,
      mensagem VARCHAR2(500) NOT NULL,
      resolvido NUMBER(1) DEFAULT 0 NOT NULL,
      data_criacao TIMESTAMP NOT NULL,
      data_resolucao TIMESTAMP,
      CONSTRAINT fk_alerta_ponto_coleta FOREIGN KEY (ponto_coleta_id)
      REFERENCES ponto_coleta(id)
);