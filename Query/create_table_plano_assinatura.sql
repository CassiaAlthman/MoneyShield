-- HABILITA O BANCO DE DADOS DO PROJETO (FINUP)
USE db_compartilhado_finup;
GO

-- VALIDA SE TABELA JÁ EXISTE NO DATABASE
IF OBJECT_ID('dbo.tbl_plano_assinatura', 'U') IS NOT NULL
	DROP TABLE dbo.tbl_plano_assinatura

GO

-- CRIA TABELA NO BANCO DE DADOS (TABELA PLANO ASSINATURA)
BEGIN

	CREATE TABLE dbo.tbl_plano_assinatura (
        cod_idef_plan	  VARCHAR         NOT NULL,
        nom_plan    	  VARCHAR         NOT NULL,
        des_plan    	  VARCHAR         NOT NULL,
        vlr_plan    	  DECIMAL(18,2)	  NOT NULL DEFAULT 0.00,
        nom_prcd_plan     VARCHAR         NOT NULL,
        qtd_limi_usua     INT             NOT NULL DEFAULT 1,
		ind_plan_ativ     BIT             NOT NULL DEFAULT 1,
		dat_hor_cria	  DATETIME        NOT NULL DEFAULT SYSUTCDATETIME(),
		dat_hor_ulti_atui DATETIME        NOT NULL,
		anomesdia		  INT             NOT NULL,

        -- ADICIONA CHAVE PRIMÁRIA COMO "cod_idef_plan"
		CONSTRAINT PK_tbl_plano_assinatura PRIMARY KEY CLUSTERED(cod_idef_plan),

        -- ADICIONA CHAVE ÚNICA COMO "nom_plan"
        CONSTRAINT UN_tbl_plano_assinatura_plan UNIQUE (nom_plan),

        -- ADICIONA VALIDAÇÃO DE DADOS NAS COLUNAS "vlr_plan", "nom_prcd_plan" e "qtd_limi_usua"
        CONSTRAINT CHK_tbl_plano_assinatura_vlr_plan  CHECK (vlr_plan >= 0),
        CONSTRAINT CHK_tbl_plano_assinatura_prcd_plan CHECK (nom_prcd_plan IN ('MENSAL', 'TRIMESTRAL', 'SEMESTRAL', 'ANUAL')),
        CONSTRAINT CHK_tbl_plano_assinatura_limi_usua CHECK (qtd_limi_usua > 0)
	);

END
GO

-- ADICIONANDO COMENTÁRIOS NOS METADADOS DA TABELA
EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Tabela que armazena os dados dos planos de assinatura do ecossistema FinUp.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Chave primária da tabela para identificação do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'cod_idef_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Nome do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'nom_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Descrição do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'des_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Valor de cobrança do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'vlr_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Periodicidade de cobrança do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'nom_prcd_plan';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Quantidade limite de usuários na conta do plano de assinatura.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'qtd_limi_usua';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Flag para indicar se o plano de assinatura está ativo ou não no sistema.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'ind_plan_ativ';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de criação do plano de assinatura cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_cria';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Data de atualização do plano de assinatura cadastrado.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'dat_hor_ulti_atui';
GO

EXEC sys.sp_addextendedproperty 
    @name = N'MS_Description', @value = N'Ano, mês e dia do processamento/ingestão de dados na tabela.',
    @level0type = N'SCHEMA', @level0name = N'dbo',
    @level1type = N'TABLE',  @level1name = N'tbl_plano_assinatura',
    @level2type = N'COLUMN', @level2name = N'anomesdia';
GO