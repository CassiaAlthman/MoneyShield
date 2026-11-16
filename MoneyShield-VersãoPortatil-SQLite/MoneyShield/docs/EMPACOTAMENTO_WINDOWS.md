# Distribuição do MoneyShield no Windows

## Versão principal

A versão principal do MoneyShield utiliza Microsoft SQL Server e permanece destinada ao ambiente de desenvolvimento e à implementação acadêmica de referência.

Para executar a versão principal, utilize o projeto Maven normalmente e configure o banco `db_moneyshield` com os scripts da pasta `database/`.

## Versão portátil

Para demonstração em outros computadores, o MoneyShield possui uma modalidade portátil baseada em SQLite.

O banco local fica em:

```text
portable/data/moneyshield.db
```

Essa modalidade não altera o banco principal SQL Server e não sincroniza seus dados com ele.

### Preparar e criar o atalho

Execute:

```text
CRIAR_ATALHO.bat
```

Na primeira preparação, o script baixa automaticamente um runtime Java para Windows e as bibliotecas necessárias. Em seguida, cria os usuários de demonstração no SQLite e gera um atalho chamado `MoneyShield` na Área de Trabalho.

O usuário final não precisa instalar Maven, IntelliJ ou SQL Server.

### Gerar um pacote totalmente offline

Na máquina que fará a distribuição, execute:

```text
GERAR_PACOTE_PORTATIL_OFFLINE.bat
```

O script prepara o runtime e as bibliotecas e cria na Área de Trabalho:

```text
MoneyShield-Portatil-Offline.zip
```

Esse ZIP pode ser enviado para outro computador. Após extrair, basta executar `CRIAR_ATALHO.bat` e abrir o atalho criado.

### Restaurar o banco portátil

Para apagar os dados locais de demonstração e retornar ao estado inicial:

```text
RESTAURAR_BANCO_PORTATIL.bat
```

Depois da restauração, execute novamente `CRIAR_ATALHO.bat` para recriar os usuários de demonstração.

## Credenciais de demonstração

Aluno:

```text
Aluno 123
Exemplo123
```

Administrador:

```text
ADM123
Exemplo123
```
