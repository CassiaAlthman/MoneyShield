-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_pessoa_login', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_pessoa_login

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA ADMIN)
BEGIN

	CREATE TABLE dbo.tbl_pessoa_login (
		cod_idef_pess	  VARCHAR	  NOT NULL,
		txt_emai		  VARCHAR	  NOT NULL,
        txt_pass		  VARCHAR	  NOT NULL,
		dat_hor_cria	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME	  NOT NULL,
		anomesdia		  INT		  NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_pess"
		CONSTRAINT PK_tbl_pessoa_login PRIMARY KEY CLUSTERED(cod_idef_pess),

		-- ADICIONA RELACIONAMENTO COM A TABELA DE PESSOA
		CONSTRAINT FK_tbl_pessoa_login_tbl_conta_pessoa
			FOREIGN KEY (cod_idef_pess)
			REFERENCES dbo.tbl_conta_pessoa (cod_idef_pess)
			ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DA PESSOA POR ELA TER LOGIN
			ON UPDATE CASCADE	-- SE O ID DA PESSOA MUDAR, ATUALIZA AUTOMÁTICO
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados de login de usuários do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária e estrangeira da tabela para identificação da pessoa.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'cod_idef_pess';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'E-mail de login do usuário no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'txt_emai';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Senha criptografada do usuário no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'txt_pass';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação do login do usuário cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização do login do usuário cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_login',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO