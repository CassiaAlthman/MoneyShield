-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_conta_pessoa', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_conta_pessoa

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA PESSOA)
BEGIN

	CREATE TABLE dbo.tbl_conta_pessoa (
		cod_idef_pess	  VARCHAR	  NOT NULL,
		nom_pess		  VARCHAR	  NOT NULL,
		num_cfcj		  VARCHAR(14) NOT NULL,
		dat_nasc		  DATE		  NOT NULL,
		ind_genr		  CHAR(2)	  NOT NULL,
		nom_carg		  VARCHAR	  NULL,
		dat_hor_cria	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME	  NOT NULL,
		anomesdia		  INT		  NOT NULL,

		-- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_pess"
		CONSTRAINT PK_tbl_conta_pessoa PRIMARY KEY CLUSTERED(cod_idef_pess),

		-- ADICIONA CHAVE ÚNICA COMO "num_cfcj"
		CONSTRAINT UN_tbl_conta_pessoa_cfcj UNIQUE (num_cfcj),

		-- ADICIONA VALIDAÇÃO DE CARACTERES PARA "ind_genr"
		CONSTRAINT CHK_tbl_conta_pessoa_genr CHECK (ind_genr IN ('MC', 'FC', 'HT', 'MT', 'NB', 'NR', 'OU'))
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela principal que armazena os dados pessoais dos usuários do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária interna da tabela para identificação da pessoa.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'cod_idef_pess';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Nome completo do usuário informado durante o cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'nom_pess';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'CPF informado durante o cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'num_cfcj';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de nascimento do usuário informado durante o cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'dat_nasc';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Gênero do usuário informado durante o cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'ind_genr';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Nome do cargo profissional do usuário informado durante o cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'nom_carg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação do usuário cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização do usuário do cadastro.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_conta_pessoa',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO
