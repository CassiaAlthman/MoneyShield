-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_aluno_badge', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_aluno_badge

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA ALUNO BADGE)
BEGIN

	CREATE TABLE dbo.tbl_aluno_badge (
        cod_idef_emss_badg	  VARCHAR	  NOT NULL,
        cod_idef_stud         VARCHAR	  NOT NULL,
        cod_idef_badg   	  VARCHAR	  NOT NULL,
        dat_hor_aqui          DATETIME    NOT NULL,
        ind_visu_badg         BIT         NOT NULL DEFAULT 0,
		dat_hor_cria    	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui     DATETIME	  NOT NULL,
		anomesdia   		  INT		  NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_emss_badg"
		CONSTRAINT PK_tbl_aluno_badge PRIMARY KEY CLUSTERED(cod_idef_emss_badg),

        -- ADICIONA CHAVE ÚNICA COMO "cod_idef_stud" + "cod_idef_badg"
        CONSTRAINT UN_tbl_aluno_badge_stud_badg UNIQUE (cod_idef_stud, cod_idef_badg),

        -- ADICIONA RELACIONAMENTO COM A TABELA DE ALUNO
        CONSTRAINT FK_tbl_aluno_badge_tbl_pessoa_aluno
            FOREIGN KEY (cod_idef_stud)
            REFERENCES dbo.tbl_pessoa_aluno (cod_idef_stud)
            ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DO ALUNO POR TER BADGE
            ON UPDATE CASCADE,  -- SE O ID DO ALUNO MUDAR, ATUALIZA AUTOMÁTICO

		-- ADICIONA RELACIONAMENTO COM A TABELA BADGE
		CONSTRAINT FK_tbl_aluno_badge_tbl_badge
			FOREIGN KEY (cod_idef_badg)
			REFERENCES dbo.tbl_badge (cod_idef_badg)
			ON DELETE NO ACTION -- IMPEDE EXCLUSÃO DO BADGE POR TER SIDO EMITIDO
			ON UPDATE CASCADE	-- SE O ID DO BADGE MUDAR, ATUALIZA AUTOMÁTICO
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados do aluno e sua badge do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária da tabela para identificação da emissão da badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'cod_idef_emss_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave estrangeira da tabela para identificação do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'cod_idef_stud';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave estrangeira da tabela para identificação da badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'cod_idef_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de aquisição da badge no perfil do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'dat_hor_aqui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Flag para indicar se o aluno já viu o pop-up da badge no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'ind_visu_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de emissão da badge do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização da badge do aluno.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_aluno_badge',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO