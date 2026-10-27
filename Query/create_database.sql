IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'db_compartilhado_finup')

BEGIN

    CREATE DATABASE db_compartilhado_finup
    ON PRIMARY (
	    NAME = N'base_de_dados_finup.DAT',
	    FILENAME = N'C:\SQL Server\base_de_dados_finup.MDF',
        SIZE = 100MB,         -- TAMANHO INICIAL DO ARQUIVO DE DADOS
        MAXSIZE = UNLIMITED,  -- SEM LIMITE DE TAMANHO
        FILEGROWTH = 64MB      -- BLOCO DE CRESCIMENTO DO ARQUIVO EM MB
    )

END
GO
