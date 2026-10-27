-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_aluno_plano_assinatura', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_aluno_plano_assinatura

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA ALUNO ASSINATURA)
BEGIN

	CREATE TABLE dbo.tbl_aluno_plano_assinatura (
        cod_idef_assn	  VARCHAR	  NOT NULL,
        cod_idef_stud     VARCHAR     NOT NULL,
		cod_idef_plan	  VARCHAR	  NOT NULL,
        dat_hor_assn      DATETIME    NOT NULL,
        dat_hor_expr      DATETIME    NOT NULL,
        nom_stat_assn     VARCHAR     NOT NULL,
        ind_renv_assn     BIT         NOT NULL DEFAULT 1,
		dat_hor_cria	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME	  NOT NULL,
		anomesdia		  INT		  NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_assn"
		CONSTRAINT PK_tbl_aluno_plano_assinatura PRIMARY KEY CLUSTERED(cod_idef_assn),

        -- ADICIONA RELACIONAMENTO COM A TABELA DE ALUNO
        CONSTRAINT FK_tbl_aluno_plano_assinatura_tbl_pessoa_aluno
            FOREIGN KEY (cod_idef_stud)
            REFERENCES dbo.tbl_pessoa_aluno (cod_idef_stud)
            ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DO ALUNO POR TER ASSINATURA
            ON UPDATE CASCADE,  -- SE O ID DO ALUNO MUDAR, ATUALIZA AUTOMÁTICO

		-- ADICIONA RELACIONAMENTO COM A TABELA PLANO DE ASSINATURA
		CONSTRAINT FK_tbl_aluno_plano_assinatura_tbl_plano_assinatura
			FOREIGN KEY (cod_idef_plan)
			REFERENCES dbo.tbl_plano_assinatura (cod_idef_plan)
			ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DO PLANO POR TER ASSINATURA
			ON UPDATE CASCADE	-- SE O ID DO PLANO MUDAR, ATUALIZA AUTOMÁTICO
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados do aluno e sua assinatura do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária da tabela para identificação da assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'cod_idef_assn';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave estrangeira da tabela para identificação do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'cod_idef_stud';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave estrangeira da tabela para identificação do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'cod_idef_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de início da assinatura no perfil do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_assn';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de expiração da assinatura no perfil do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_expr';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Nome do status da assinatura no perfil do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'nom_stat_assn';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Flag para indicar se a assinatura será ou não renovada automaticamente no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'ind_renv_assn';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação da assinatura do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização da assinatura do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO