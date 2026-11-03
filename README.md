<p align="center">
  <img src="MoneyShieldV1Final/MoneyShieldV1Final/MoneyShield/src/main/resources/images/moneyshield-icon-256.png" width="160" />
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

O **MoneyShield** é um protótipo funcional de educação financeira gamificada desenvolvido com o objetivo de apoiar jovens no aprendizado e na prática de conceitos financeiros por meio de trilhas, avaliações, missões, simulações e metas.

A aplicação combina conteúdo educacional com elementos de gamificação, como experiência (XP), níveis, progresso, missões e conquistas.

O projeto foi desenvolvido em **Java com interface gráfica Swing**, utilizando uma arquitetura de monólito modular organizada em diferentes pacotes e responsabilidades.

Atualmente o MoneyShield possui duas formas de execução:

- **Versão principal:** utiliza Microsoft SQL Server para persistência dos dados.
- **Versão portátil:** utiliza SQLite, permitindo que o banco de dados acompanhe a própria pasta da aplicação sem exigir a instalação do SQL Server no computador utilizado.

A versão principal permanece como referência de desenvolvimento do projeto. A versão SQLite foi criada especificamente para facilitar demonstrações, testes e distribuição do protótipo.

---

## Funcionalidades

### Aluno

- Cadastro de novos alunos.
- Autenticação com usuário ou e-mail.
- Senhas armazenadas utilizando hash **BCrypt**.
- Sessão individual por usuário.
- Trilhas de educação financeira.
- Avaliações pré e pós-trilha vinculadas ao aluno.
- Registro persistente das respostas das avaliações.
- Sistema de XP e níveis.
- Missões com condições verificáveis pelo sistema.
- Conquistas relacionadas ao progresso.
- Simulações financeiras:
  - juros simples;
  - juros compostos;
  - fatura e pagamento mínimo;
  - cenários de tomada de decisão.
- Metas financeiras com acompanhamento de progresso.
- Histórico de atividades.
- Perfil com informações de evolução.

### Administrador

- Login separado de administrador.
- Gerenciamento persistente de trilhas.
- Gerenciamento persistente de missões.
- Gerenciamento persistente de cenários financeiros.
- Gerenciamento de avaliações.
- Consulta dos alunos cadastrados.
- Consulta de XP e nível.
- Consulta do progresso em trilhas.
- Consulta de missões e simulações realizadas.
- Consulta dos resultados das avaliações pré e pós vinculadas ao aluno selecionado.

---

## Versões disponíveis

### Versão principal — SQL Server

A versão principal do MoneyShield utiliza:

```text
Java
   ↓
JDBC
   ↓
Microsoft SQL Server
```

Essa é a configuração utilizada durante o desenvolvimento do sistema e mantém os dados no banco:

```text
db_moneyshield
```

A aplicação espera, por padrão, uma instância do SQL Server disponível em:

```text
localhost:1433
```

Essa versão é indicada para desenvolvimento, manutenção do código, validação da persistência e utilização do projeto em ambiente configurado com SQL Server.

---

### Versão portátil — SQLite

Para facilitar a distribuição e demonstração do projeto, foi criada uma versão portátil utilizando **SQLite**.

Seu fluxo é:

```text
Java
   ↓
JDBC
   ↓
SQLite
   ↓
portable/data/moneyshield.db
```

O banco fica armazenado dentro da própria estrutura da aplicação:

```text
portable/data/moneyshield.db
```

Dessa forma, a versão portátil não exige:

- instalação do Microsoft SQL Server;
- criação manual do banco `db_moneyshield`;
- SQL Server Management Studio;
- configuração de usuário do SQL Server.

Os dados cadastrados durante a utilização da versão portátil permanecem armazenados no arquivo SQLite local.

> A utilização do SQLite na versão portátil não substitui a arquitetura principal documentada do projeto. Trata-se de uma alternativa de distribuição criada para facilitar a execução do protótipo em outros computadores.

---

## Como usar a versão portátil

Após baixar o projeto ou receber o pacote portátil:

1. Extraia toda a pasta do MoneyShield.
2. Não execute o programa diretamente de dentro do arquivo `.zip`.
3. Abra a pasta extraída.
4. Execute:

```text
CRIAR_ATALHO.bat
```

O script prepara o ambiente necessário e cria um atalho chamado **MoneyShield** na Área de Trabalho.

Após a criação, basta utilizar o atalho:

```text
MoneyShield
```

para iniciar a aplicação.

O atalho utiliza internamente:

```text
ABRIR_MONEYSHIELD.bat
```

Por isso, a pasta original do MoneyShield deve permanecer no computador.

### Arquivos utilizados pela versão portátil

```text
MoneyShield/
├── ABRIR_MONEYSHIELD.bat
├── CRIAR_ATALHO.bat
├── portable/
│   └── data/
│       └── moneyshield.db
├── runtime/
├── app/
├── /
├── database/
├── docs/
├── packaging/
├── pom.xml
└── README.md
```

O usuário não precisa abrir o `ABRIR_MONEYSHIELD.bat` manualmente depois que o atalho for criado.

---

## Pacote portátil offline

Durante a preparação do projeto também pode ser utilizado:

```text
GERAR_PACOTE_PORTATIL_OFFLINE.bat
```

Esse script é destinado à equipe de desenvolvimento e permite gerar uma distribuição portátil contendo os componentes necessários para execução.

O objetivo dessa versão é permitir que o computador de destino execute o MoneyShield sem precisar instalar manualmente:

- Java;
- Maven;
- IntelliJ IDEA;
- SQL Server;
- SQL Server Management Studio.

Depois de gerar o pacote offline, o fluxo para o usuário final continua sendo:

```text
Extrair a pasta
      ↓
CRIAR_ATALHO.bat
      ↓
Atalho MoneyShield
      ↓
Aplicação
```

---

## Tecnologias

| Tecnologia | Uso |
| --- | --- |
| Java 24 | Linguagem principal |
| Java Swing | Interface gráfica desktop |
| Maven | Gerenciamento de dependências e build |
| JDBC | Comunicação com os bancos de dados |
| Microsoft SQL Server | Persistência utilizada na versão principal |
| Microsoft JDBC Driver | Driver de conexão da versão SQL Server |
| SQLite | Persistência local utilizada na versão portátil |
| SQLite JDBC | Comunicação entre Java e SQLite |
| BCrypt | Hash e validação de senhas |
| Git/GitHub | Versionamento do código |
| PowerShell / Batch | Preparação e inicialização da versão portátil |

---

## Dependências principais

Entre as dependências utilizadas pelo projeto estão:

```xml
com.microsoft.sqlserver:mssql-jdbc:13.6.0.jre11
at.favre.lib:bcrypt:0.10.2
```

A versão portátil também utiliza o driver JDBC necessário para comunicação com SQLite.

---

## Arquitetura

O projeto utiliza um **monólito modular**, separando responsabilidades por pacotes.

```text
src/main/java/br/com/moneyshield/
├── app/
│   └── ponto de entrada da aplicação
├── dao/
│   └── acesso e persistência de dados
├── dev/
│   └── utilitários de desenvolvimento
├── infrastructure/
│   └── infraestrutura e conexão com o banco
├── model/
│   └── entidades e objetos de domínio
├── repository/
│   └── organização das operações relacionadas aos dados
├── service/
│   └── regras compartilhadas de negócio e gamificação
├── session/
│   └── autenticação e sessão do usuário
└── ui/
    ├── admin/
    ├── auth/
    ├── student/
    └── theme/
```

### Fluxo da versão principal

```text
Interface Swing
      ↓
Repository / Service
      ↓
DAO
      ↓
JDBC
      ↓
Microsoft SQL Server
```

### Fluxo da versão portátil

```text
Interface Swing
      ↓
Repository / Service
      ↓
DAO
      ↓
JDBC
      ↓
SQLite
      ↓
moneyshield.db
```

A estrutura interna da aplicação permanece a mesma. A principal diferença entre as versões está no mecanismo utilizado para persistência dos dados.

---

## Estrutura do projeto

```text
MoneyShield/
├── database/
│   ├── 00_INSTALACAO_LIMPA.sql
│   ├── 01_SEED_INICIAL.sql
│   ├── 02_USUARIO_DEMO_OPCIONAL.sql
│   ├── 03_CORRECOES_PRIORIDADES_ALTAS.sql
│   └── 04_USUARIO_APLICACAO.sql
├── docs/
│   ├── EMPACOTAMENTO_WINDOWS.md
│   ├── ESTRUTURA_CLASSES.txt
│   ├── ORGANIZACAO_PROJETO.md
│   └── PORTABILIDADE_SQLITE.md
├── packaging/
│   └── windows/
├── portable/
│   └── data/
│       └── moneyshield.db
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── ABRIR_MONEYSHIELD.bat
├── CRIAR_ATALHO.bat
├── GERAR_PACOTE_PORTATIL_OFFLINE.bat
├── pom.xml
└── README.md
```

---

## Requisitos para desenvolvimento

Para trabalhar diretamente com o código-fonte e com a versão principal:

- Windows 10 ou Windows 11;
- JDK 24 ou superior;
- Apache Maven;
- Microsoft SQL Server;
- SQL Server Management Studio, recomendado para execução dos scripts;
- IntelliJ IDEA ou outra IDE Java compatível com Maven.

A versão principal espera o SQL Server em:

```text
localhost:1433
```

Banco utilizado:

```text
db_moneyshield
```

Esses requisitos não se aplicam da mesma forma ao usuário que utiliza apenas a distribuição portátil.

---

## Configuração do banco SQL Server

Os scripts utilizados pela versão principal estão na pasta:

```text
database/
```

### Instalação limpa

Execute os scripts na seguinte ordem:

```text
00_INSTALACAO_LIMPA.sql
01_SEED_INICIAL.sql
03_CORRECOES_PRIORIDADES_ALTAS.sql
04_USUARIO_APLICACAO.sql
```

O arquivo:

```text
02_USUARIO_DEMO_OPCIONAL.sql
```

é opcional.

> **Atenção:** `00_INSTALACAO_LIMPA.sql` remove as tabelas existentes antes de recriá-las. Não execute esse script em um banco que contenha informações que precisam ser preservadas.

### Usuário SQL da aplicação

O script:

```text
04_USUARIO_APLICACAO.sql
```

cria o login utilizado pela aplicação principal.

```text
Usuário: moneyshield_app
Banco: db_moneyshield
Permissões: db_datareader e db_datawriter
```

A configuração correspondente encontra-se na infraestrutura de banco do projeto.

---

## Banco SQLite portátil

A versão portátil utiliza:

```text
portable/data/moneyshield.db
```

Esse arquivo concentra os dados persistidos pela distribuição portátil.

Entre as informações armazenadas estão:

- contas e autenticação;
- perfis de aluno e administrador;
- progresso geral;
- XP;
- nível;
- trilhas;
- progresso por trilha;
- avaliações pré e pós;
- respostas das avaliações;
- missões;
- progresso das missões;
- simulações;
- cenários financeiros;
- metas financeiras.

### Importante

Se o arquivo:

```text
moneyshield.db
```

for excluído, os dados armazenados naquela cópia portátil podem ser perdidos.

Para preservar os dados de uma utilização específica, mantenha uma cópia do arquivo antes de excluir ou substituir a pasta da aplicação.

---

## Usuários de demonstração

O projeto possui credenciais destinadas a demonstrações e testes.

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

O cadastro público da aplicação permite criar apenas contas de **Aluno**.

Contas administrativas não podem ser criadas pela tela pública de cadastro.

---

## Executando pelo IntelliJ IDEA

Para utilizar a versão principal durante o desenvolvimento:

1. Clone ou extraia o projeto.
2. Abra o arquivo `pom.xml` como projeto Maven.
3. Aguarde o Maven carregar as dependências.
4. Configure o Microsoft SQL Server.
5. Execute os scripts da pasta `database/`.
6. Certifique-se de que o SQL Server está disponível em `localhost:1433`.
7. Execute a classe:

```text
br.com.moneyshield.app.EducacaoFinanceiraMain
```

---

## Executando pelo Maven

Na raiz do projeto:

```bash
mvn clean package
```

O Maven é utilizado para compilar o projeto e preparar suas dependências.

A classe principal da aplicação é:

```text
br.com.moneyshield.app.EducacaoFinanceiraMain
```

O Maven é necessário para desenvolvimento e build do código-fonte, mas **não é necessário para o usuário final da versão portátil já preparada**.

---

## Ícone e identidade visual

O ícone do MoneyShield está armazenado em diferentes resoluções em:

```text
src/main/resources/images/
```

A versão utilizada pelos recursos do Windows encontra-se em:

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

---

## Persistência

A aplicação possui dois mecanismos de persistência de acordo com a versão utilizada.

### SQL Server

Utilizado na versão principal do projeto.

Indicado para:

- desenvolvimento;
- manutenção;
- testes no ambiente principal;
- utilização do banco centralizado do projeto.

### SQLite

Utilizado na versão portátil.

Indicado para:

- demonstrações;
- apresentação acadêmica;
- execução em outros computadores;
- testes sem necessidade de instalação do SQL Server.

Em ambos os casos, a aplicação mantém a separação dos dados de cada aluno por meio da identificação do usuário autenticado.

---

## Segurança

As senhas dos usuários são armazenadas utilizando **BCrypt**, evitando o armazenamento direto das credenciais em texto simples.

O cadastro público não permite criar contas administrativas. Essa regra foi reforçada durante a revisão da especificação do projeto.

As consultas relacionadas ao progresso, avaliações, missões, simulações e demais atividades são associadas ao identificador do usuário correspondente.

A versão portátil mantém essas mesmas regras de autenticação e separação de usuários, alterando apenas o mecanismo utilizado para persistência.

---

## Observação sobre o ambiente acadêmico

A configuração SQL Server utilizada no projeto foi desenvolvida para o ambiente acadêmico e de demonstração do protótipo.

A versão SQLite foi adicionada posteriormente como alternativa de portabilidade para permitir que avaliadores e outros usuários possam executar a aplicação sem precisar configurar previamente um servidor de banco de dados.

As duas versões utilizam o mesmo objetivo funcional e fazem parte do mesmo projeto MoneyShield.

---

## Objetivo acadêmico

O MoneyShield busca demonstrar como
