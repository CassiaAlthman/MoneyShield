# Portabilidade SQLite

A implementação principal do MoneyShield utiliza Microsoft SQL Server. Para permitir a demonstração do protótipo sem instalar um servidor de banco de dados no computador de destino, foi adicionada uma modalidade portátil com SQLite.

A seleção do banco ocorre em `ConexaoBanco`:

- sem configuração adicional: SQL Server;
- com `-Dmoneyshield.database=sqlite`: SQLite.

O launcher `ABRIR_MONEYSHIELD.bat` ativa automaticamente o modo SQLite e informa o caminho de `portable/data/moneyshield.db`.

Para evitar a duplicação das DAOs e repositórios, a classe `SqliteSqlAdapter` adapta, somente no modo portátil, construções originalmente utilizadas pelo SQL Server, como `TOP`, `OUTPUT INSERTED`, `ISNULL`, `SYSUTCDATETIME()` e o prefixo `dbo.`.

Os scripts equivalentes do SQLite estão em:

```text
database/sqlite/00_SCHEMA_SQLITE.sql
database/sqlite/01_SEED_SQLITE.sql
```

O SQLite portátil é uma facilidade de distribuição e demonstração. Ele não substitui a arquitetura principal documentada com SQL Server e não possui sincronização automática com o banco principal.
