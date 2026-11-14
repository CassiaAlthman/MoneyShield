# Empacotamento do MoneyShield no Windows

## Resultado

O projeto usa `jpackage` para gerar um aplicativo Windows com:

- `MoneyShield.exe`;
- runtime Java embutido;
- ícone próprio;
- dependências Java dentro do JAR executável;
- instalador local simples em Batch;
- atalhos no Desktop e Menu Iniciar.

O instalador não exige privilégios de administrador porque instala em:

`%LOCALAPPDATA%\Programs\MoneyShield`

## Pré-requisitos para gerar o executável

Apenas a máquina que gera a distribuição precisa de:

1. Windows 10 ou Windows 11;
2. JDK 24 ou superior com `java`, `javac` e `jpackage` no `PATH`;
3. Apache Maven com `mvn` no `PATH`.

O usuário final não precisa instalar Java, Maven ou JDK.

## Gerar o executável

Na raiz do projeto, execute:

`CRIAR_EXECUTAVEL.bat`

O script:

1. executa `mvn clean package -DskipTests`;
2. gera um JAR executável com todas as dependências;
3. executa `jpackage --type app-image`;
4. cria `dist\MoneyShield\MoneyShield.exe`;
5. copia scripts SQL e documentação para `dist`;
6. gera `MoneyShield-Windows-1.0.0.zip`.

## Gerar e instalar na mesma máquina

Execute:

`CRIAR_E_INSTALAR.bat`

Depois da geração, o instalador local é iniciado automaticamente.

## Distribuição

O arquivo para enviar a outra pessoa é:

`MoneyShield-Windows-1.0.0.zip`

Após extrair o ZIP, a pessoa executa:

`INSTALAR_MONEYSHIELD.bat`

Também é possível abrir diretamente:

`MoneyShield\MoneyShield.exe`

sem instalar atalhos.

## Banco de dados

O executável não instala o SQL Server automaticamente.

Para uma instalação nova do ambiente de demonstração, use os scripts da pasta `database` na seguinte ordem:

1. `00_INSTALACAO_LIMPA.sql`
2. `01_SEED_INICIAL.sql`
3. `02_USUARIO_DEMO_OPCIONAL.sql`
4. `03_CORRECOES_PRIORIDADES_ALTAS.sql`
5. `04_USUARIO_APLICACAO.sql`

O último script cria o login SQL utilizado pela aplicação.

## Desinstalação

O instalador cria a opção `Desinstalar MoneyShield` no Menu Iniciar.

A desinstalação remove apenas os arquivos locais do aplicativo e os atalhos. O banco SQL Server não é apagado.
