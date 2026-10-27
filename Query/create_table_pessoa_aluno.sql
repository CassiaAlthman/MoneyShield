-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_pessoa_aluno', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_pessoa_aluno

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA ALUNO)
BEGIN

	CREATE TABLE dbo.tbl_pessoa_aluno (
        cod_idef_stud	  VARCHAR	  NOT NULL,
		cod_idef_pess	  VARCHAR	  NOT NULL,
		ind_usua_ativ     BIT         NOT NULL,
		dat_hor_cria	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME	  NOT NULL,
		anomesdia		  INT		  NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_stud"
		CONSTRAINT PK_tbl_pessoa_aluno PRIMARY KEY CLUSTERED(cod_idef_stud),

		-- ADICIONA RELACIONAMENTO COM A TABELA DE PESSOA
		CONSTRAINT FK_tbl_pessoa_aluno_tbl_conta_pessoa
			FOREIGN KEY (cod_idef_pess)
			REFERENCES dbo.tbl_conta_pessoa (cod_idef_pess)
			ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DA PESSOA POR ELA SER ALUNO
			ON UPDATE CASCADE	-- SE O ID DA PESSOA MUDAR, ATUALIZA AUTOMÁTICO
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados do aluno de usuários do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária da tabela para identificação do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'cod_idef_stud';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave estrangeira da tabela para identificação da pessoa.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'cod_idef_pess';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Flag para indicar se o aluno está ativo ou não no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'ind_usua_ativ';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação do perfil de aluno do usuário cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização do perfil de aluno do usuário cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_pessoa_aluno',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO