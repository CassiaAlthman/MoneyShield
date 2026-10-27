-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_badge', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_badge

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA BADGE)
BEGIN

	CREATE TABLE dbo.tbl_badge (
        cod_idef_badg	  VARCHAR	  NOT NULL,
        nom_badg          VARCHAR     NOT NULL,
        des_badg          VARCHAR     NOT NULL,
        txt_url_badg      VARCHAR     NOT NULL,
        qtd_pont_badg     INT         NOT NULL,
        ind_badg_ativ     BIT         NOT NULL DEFAULT 1,
		dat_hor_cria	  DATETIME	  NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME	  NOT NULL,
		anomesdia		  INT		  NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_badg"
		CONSTRAINT PK_tbl_badge PRIMARY KEY CLUSTERED(cod_idef_badg),

        -- ADICIONA CHAVE ÚNICA COMO "nom_badg"
        CONSTRAINT UN_tbl_badge_badg UNIQUE (nom_badg),

        -- ADICIONA VALIDAÇÃO DE DADOS NA COLUNA "qtd_pont_badg"
        CONSTRAINT CHK_tbl_badge_pont_badg CHECK (qtd_pont_badg >= 0)
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados dos badges do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária da tabela para identificação do badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'cod_idef_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Nome do badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'nom_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Descrição dos requisitos do badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'des_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Caminho da URL da imagem do badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'txt_url_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Quantidade de pontos necessários para adquirir o badge.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'qtd_pont_badg';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Flag para indicar se o badge está ativo ou não no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'ind_badg_ativ';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação do badge cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização do badge cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_badge',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO