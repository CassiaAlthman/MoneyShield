USE master;
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.server_principals
    WHERE name = N'moneyshield_app'
)
BEGIN
    EXEC(
        N'CREATE LOGIN [moneyshield_app]
          WITH PASSWORD = ''MoneyShield_Local_2026!'',
               CHECK_POLICY = ON,
               CHECK_EXPIRATION = OFF;'
    );
END
GO

IF DB_ID(N'db_moneyshield') IS NULL
BEGIN
    THROW 50001, 'O banco db_moneyshield ainda não existe. Execute primeiro 00_INSTALACAO_LIMPA.sql.', 1;
END
GO

USE db_moneyshield;
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.database_principals
    WHERE name = N'moneyshield_app'
)
BEGIN
    CREATE USER [moneyshield_app]
    FOR LOGIN [moneyshield_app];
END
GO

IF COALESCE(IS_ROLEMEMBER(N'db_datareader', N'moneyshield_app'), 0) <> 1
BEGIN
    ALTER ROLE [db_datareader]
    ADD MEMBER [moneyshield_app];
END
GO

IF COALESCE(IS_ROLEMEMBER(N'db_datawriter', N'moneyshield_app'), 0) <> 1
BEGIN
    ALTER ROLE [db_datawriter]
    ADD MEMBER [moneyshield_app];
END
GO

PRINT 'Usuário moneyshield_app configurado com sucesso.';
GO
