<p align="center">
  <img src="src/main/resources/images/moneyshield-icon-256.png" alt="Ícone do MoneyShield" width="160" />
</p>

<h1 align="center">MoneyShield</h1>

<p align="center">
  Sistema de Educação Financeira Gamificada para Jovens
</p>

<p align="center">
  <strong>Java Swing • JDBC • SQL Server • SQLite • Maven • BCrypt</strong>
</p>

---

## Sobre o projeto

O **MoneyShield** é um protótipo funcional de educação financeira gamificada voltado principalmente ao público jovem. A aplicação reúne trilhas de aprendizagem, avaliações pré e pós-trilha, missões, XP, níveis, conquistas, metas financeiras e simulações de situações do cotidiano.

O projeto foi desenvolvido em **Java com Java Swing**, utilizando uma arquitetura de monólito modular. A versão principal utiliza **Microsoft SQL Server** para persistência. Para facilitar demonstrações e distribuição em outros computadores, o projeto também possui uma **versão portátil com SQLite**.

## Duas formas de execução

### 1. Versão principal — SQL Server

Esta é a versão utilizada como referência principal do projeto e da documentação acadêmica.

Fluxo:

```text
Java Swing
   ↓
Services / Repositories / DAOs
   ↓
JDBC
   ↓
Microsoft SQL Server
```

Características:

- banco `db_moneyshield` em Microsoft SQL Server;
- conexão padrão em `localhost:1433`;
- scripts SQL oficiais na pasta `database/`;
- indicada para desenvolvimento, testes e execução do projeto principal;
- quando nenhuma configuração especial é fornecida, o MoneyShield utiliza SQL Server automaticamente.

### 2. Versão portátil — SQLite

A versão portátil foi criada para permitir que o MoneyShield seja demonstrado em outro computador sem exigir instalação ou configuração do Microsoft SQL Server.

Fluxo:

```text
Java Swing
   ↓
Services / Repositories / DAOs
   ↓
JDBC
   ↓
SQLite
   ↓
portable/data/moneyshield.db
```

O SQLite é utilizado **somente como alternativa de distribuição portátil**. A versão principal do projeto continua utilizando SQL Server.

Os dados gravados em SQLite são locais e independentes. Eles **não são sincronizados automaticamente** com o banco SQL Server.

## Uso rápido da versão portátil

No computador que receber o projeto:

1. Extraia toda a pasta do MoneyShield.
2. Execute `CRIAR_ATALHO.bat`.
3. Aguarde a preparação inicial.
4. Abra o atalho **MoneyShield** criado na Área de Trabalho.

Na primeira preparação, se o pacote ainda não contiver o runtime e as bibliotecas, o script baixa automaticamente:

- runtime Java para Windows;
- SQLite JDBC;
- BCrypt e sua dependência;
- biblioteca de logging necessária ao SQLite JDBC.

Depois dessa preparação, a versão pode ser executada sem Maven, IntelliJ ou SQL Server.

### Pacote portátil totalmente offline

Antes de enviar o projeto para outra pessoa, execute:

```text
GERAR_PACOTE_PORTATIL_OFFLINE.bat
```

Esse processo prepara o runtime e as bibliotecas e gera na Área de Trabalho:

```text
MoneyShield-Portatil-Offline.zip
```

Quem receber esse ZIP precisa apenas:

```text
Extrair → CRIAR_ATALHO.bat → abrir o atalho MoneyShield
```

Nesse caso, não é necessário baixar Java ou dependências no computador de destino.

## Banco SQLite portátil

O arquivo utilizado pela versão portátil fica em:

```text
portable/data/moneyshield.db
```

Ele já contém a estrutura e os dados iniciais utilizados pelo protótipo, incluindo:

- níveis;
- badges/conquistas;
- 12 trilhas;
- 5 missões;
- cenários financeiros e alternativas;
- avaliações pré e pós da Trilha 1;
- perguntas e alternativas das avaliações.

Durante a primeira preparação, o sistema cria também os usuários de demonstração utilizando BCrypt.

Para restaurar o banco portátil ao estado inicial, execute:

```text
RESTAURAR_BANCO_PORTATIL.bat
```

> A restauração remove os dados criados durante o uso da versão portátil.

## Usuários de demonstração

### Aluno

```text
Usuário: Aluno 123
Senha: Exemplo123
```

### Administrador

```text
Usuário: ADM123
Senha: Exemplo123
```

O cadastro público da aplicação cria apenas contas de aluno. Contas administrativas não são criadas pela tela de cadastro.

## Funcionalidades

### Aluno

- cadastro de novos alunos;
- autenticação com usuário ou e-mail;
- senhas protegidas com BCrypt;
- sessão individual por usuário;
- trilhas de educação financeira;
- avaliações pré e pós-trilha;
- persistência das respostas e resultados;
- XP e níveis;
- missões e conquistas;
- simulador de juros simples e compostos;
- simulador de fatura e pagamento mínimo;
- cenários de tomada de decisão;
- metas financeiras;
- acompanhamento de progresso e perfil.

### Administrador

- autenticação separada de administrador;
- gerenciamento de trilhas;
- gerenciamento de missões;
- gerenciamento de cenários financeiros;
- gerenciamento de avaliações;
- consulta de alunos cadastrados;
- consulta de XP, nível, trilhas, missões e simulações;
- consulta dos resultados das avaliações.

## Tecnologias

| Tecnologia | Uso no projeto |
| --- | --- |
| Java 24 | Linguagem principal do projeto |
| Java Swing | Interface gráfica desktop |
| Maven | Dependências e build da versão de desenvolvimento |
| JDBC | Camada de comunicação com os bancos |
| Microsoft SQL Server | Banco da versão principal |
| Microsoft JDBC Driver | Driver da versão principal |
| SQLite | Banco local da versão portátil |
| SQLite JDBC | Driver da versão portátil |
| BCrypt | Hash e validação de senhas |
| Git/GitHub | Versionamento do código |
| PowerShell / BAT | Preparação e inicialização da distribuição portátil no Windows |

### Dependências Maven principais

```xml
com.microsoft.sqlserver:mssql-jdbc:13.6.0.jre11
at.favre.lib:bcrypt:0.10.2
org.xerial:sqlite-jdbc:3.46.1.0
```

## Arquitetura

O projeto utiliza um **monólito modular** organizado por responsabilidade:

```text
src/main/java/br/com/moneyshield/
├── app/
│   └── ponto de entrada da aplicação
├── dao/
│   └── acesso aos dados
├── dev/
│   └── utilitários de desenvolvimento e validação
├── infrastructure/
│   └── conexão e adaptação de banco de dados
├── model/
│   └── objetos de domínio
├── repository/
│   └── operações relacionadas aos dados das funcionalidades
├── service/
│   └── regras de negócio e gamificação
├── session/
│   └── sessão do usuário autenticado
└── ui/
    ├── admin/
    ├── auth/
    ├── student/
    └── theme/
```

A classe `ConexaoBanco` utiliza SQL Server por padrão. Quando a aplicação é iniciada pelo launcher portátil, a propriedade `moneyshield.database=sqlite` ativa a conexão com o arquivo SQLite local. Um adaptador interno converte as construções SQL específicas necessárias para preservar o funcionamento das mesmas DAOs e repositórios na versão portátil.

## Estrutura principal

```text
MoneyShield/
├── app/
│   └── MoneyShield.jar
├── database/
│   ├── 00_INSTALACAO_LIMPA.sql
│   ├── 01_SEED_INICIAL.sql
│   ├── 02_USUARIO_DEMO_OPCIONAL.sql
│   ├── 03_CORRECOES_PRIORIDADES_ALTAS.sql
│   ├── 04_USUARIO_APLICACAO.sql
│   └── sqlite/
│       ├── 00_SCHEMA_SQLITE.sql
│       └── 01_SEED_SQLITE.sql
├── portable/
│   ├── data/
│   │   └── moneyshield.db
│   └── template/
│       └── moneyshield-template.db
├── src/
├── packaging/
├── CRIAR_ATALHO.bat
├── ABRIR_MONEYSHIELD.bat
├── PREPARAR_PORTATIL.ps1
├── GERAR_PACOTE_PORTATIL_OFFLINE.bat
├── RESTAURAR_BANCO_PORTATIL.bat
├── pom.xml
└── README.md
```

As pastas `runtime/` e `lib/` são preenchidas durante a preparação da versão portátil e podem ser incluídas no pacote offline.

## Executando a versão principal pelo IntelliJ IDEA

1. Abra o `pom.xml` como projeto Maven.
2. Aguarde o download das dependências.
3. Instale e configure o Microsoft SQL Server.
4. Execute os scripts da pasta `database/` na ordem indicada abaixo.
5. Confirme que o SQL Server está disponível em `localhost:1433`.
6. Execute:

```text
br.com.moneyshield.app.EducacaoFinanceiraMain
```

Sem a propriedade `moneyshield.database=sqlite`, a aplicação utiliza automaticamente o SQL Server.

## Configuração do SQL Server — versão principal

Execute:

```text
00_INSTALACAO_LIMPA.sql
01_SEED_INICIAL.sql
03_CORRECOES_PRIORIDADES_ALTAS.sql
04_USUARIO_APLICACAO.sql
```

O banco utilizado é:

```text
db_moneyshield
```

A configuração padrão da aplicação utiliza:

```text
Servidor: localhost:1433
Usuário: moneyshield_app
```

> `00_INSTALACAO_LIMPA.sql` recria a estrutura do banco. Não execute esse script sobre dados que precisam ser preservados.

## Persistência

Nas duas modalidades são persistidos os principais dados da aplicação, como:

- contas e autenticação;
- progresso do aluno;
- XP e nível;
- trilhas e conclusões;
- avaliações pré e pós;
- respostas das avaliações;
- missões e progresso;
- simulações;
- cenários financeiros;
- metas financeiras;
- conteúdos administrativos.

A diferença está no mecanismo de armazenamento:

- **versão principal:** Microsoft SQL Server;
- **versão portátil:** arquivo SQLite local.

## Segurança

As senhas dos usuários são protegidas utilizando **BCrypt**.

O cadastro público não permite criar administradores. As operações relacionadas ao aluno são associadas ao usuário autenticado, evitando o compartilhamento de progresso entre contas diferentes.

A versão portátil foi criada para demonstração acadêmica. Como o banco SQLite fica dentro da própria pasta distribuída, ele não deve ser tratado como substituto de uma infraestrutura de produção com controles de acesso, backup e administração centralizada.

## Ícone e identidade visual

O ícone utilizado no Windows está em:

```text
packaging/windows/MoneyShield.ico
```

A identidade visual utiliza principalmente:

```text
Marinho: #060D28
Amarelo: #FDBF6B
Rosa:    #EC116C
Azul:    #6BA4E1
Malva:   #B55C77
```

## Objetivo acadêmico

O MoneyShield busca demonstrar como recursos de gamificação podem ser integrados a uma aplicação de educação financeira para apoiar participação, acompanhamento de progresso e prática de decisões financeiras.

A versão SQL Server permanece como implementação principal documentada do projeto. A versão SQLite existe como mecanismo complementar de portabilidade e demonstração.
