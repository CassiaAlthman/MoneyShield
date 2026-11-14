# Organização do código-fonte do MoneyShield

A estrutura foi migrada do pacote padrão para o layout convencional do Maven:

```text
src/main/java/br/com/moneyshield/
├── app/
├── dao/
├── dev/
├── infrastructure/database/
├── model/
├── repository/
├── service/
├── session/
└── ui/
    ├── admin/
    ├── auth/
    ├── student/
    └── theme/
```

## Responsabilidades

- `app`: ponto de entrada da aplicação.
- `dao`: acesso direto às tabelas do SQL Server.
- `dev`: utilitários de desenvolvimento e preparação de usuários de teste.
- `infrastructure.database`: configuração e abertura de conexões JDBC.
- `model`: entidades e objetos de domínio.
- `repository`: camada usada pelas telas e modelos para acessar dados persistidos.
- `service`: regras compartilhadas de gamificação e progresso.
- `session`: usuário autenticado e estado da sessão atual.
- `ui.admin`: telas exclusivas do administrador.
- `ui.auth`: telas de autenticação/cadastro.
- `ui.student`: telas exclusivas do aluno.
- `ui.theme`: tema visual central do MoneyShield.

## Renomes realizados

- `propostadeGame` → `GamificacaoService`
- `CenariosFinanceirosADM` → `CenariosFinanceirosAdmin`
- `GerenciamentoAlunosADM` → `GerenciamentoAlunosAdmin`
- `GerenciamentoAvaliacoesADM` → `GerenciamentoAvaliacoesAdmin`
- `MissoesDesafiosADM` → `MissoesDesafiosAdmin`

## Limpeza

- `PerfilAluno.java` foi removido porque não era referenciado por nenhuma parte da aplicação. O perfil utilizado atualmente está integrado ao fluxo principal do MoneyShield.
- Arquivos `.idea`, `.iml`, `.class` e `target` não fazem parte da versão organizada e estão cobertos pelo `.gitignore`.
- O código passou a usar `Locale.of("pt", "BR")` em vez do construtor de `Locale` obsoleto.

## Banco de dados

Os scripts foram centralizados em `database/` e devem ser executados na ordem numérica quando for necessária uma instalação limpa.

## Recursos

A pasta `src/main/resources/images/` já foi criada para receber o ícone oficial do MoneyShield na próxima etapa.
