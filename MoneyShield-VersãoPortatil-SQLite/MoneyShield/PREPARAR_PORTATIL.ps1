param(
    [switch]$Silencioso
)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"

$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
$RuntimeDir = Join-Path $Root "runtime"
$LibDir = Join-Path $Root "lib"
$AppJar = Join-Path $Root "app\MoneyShield.jar"
$DbPath = Join-Path $Root "portable\data\moneyshield.db"
$Marker = Join-Path $Root "portable\data\.usuarios-demo-inicializados"
$TempDir = Join-Path $env:TEMP ("MoneyShieldPortable_" + [Guid]::NewGuid().ToString("N"))

function Mensagem([string]$Texto) {
    if (-not $Silencioso) {
        Write-Host $Texto
    }
}

function Baixar-Arquivo([string]$Url, [string]$Destino) {
    Mensagem "Baixando: $([System.IO.Path]::GetFileName($Destino))"
    Invoke-WebRequest -UseBasicParsing -Uri $Url -OutFile $Destino
}

try {
    if (-not (Test-Path $AppJar)) {
        throw "O arquivo app\MoneyShield.jar nao foi encontrado."
    }

    if (-not (Test-Path $DbPath)) {
        throw "O banco SQLite portatil portable\data\moneyshield.db nao foi encontrado."
    }

    New-Item -ItemType Directory -Force -Path $RuntimeDir | Out-Null
    New-Item -ItemType Directory -Force -Path $LibDir | Out-Null
    New-Item -ItemType Directory -Force -Path $TempDir | Out-Null

    $Javaw = Join-Path $RuntimeDir "bin\javaw.exe"
    $JavaExe = Join-Path $RuntimeDir "bin\java.exe"

    if (-not (Test-Path $Javaw)) {
        Mensagem "Preparando o Java portatil do MoneyShield..."

        $JreZip = Join-Path $TempDir "temurin24-jre-windows.zip"
        $JreExtract = Join-Path $TempDir "jre"

        Baixar-Arquivo `
            "https://api.adoptium.net/v3/binary/latest/24/ga/windows/x64/jre/hotspot/normal/eclipse" `
            $JreZip

        Expand-Archive -Path $JreZip -DestinationPath $JreExtract -Force

        $JreHome = Get-ChildItem -Path $JreExtract -Directory -Recurse |
            Where-Object { Test-Path (Join-Path $_.FullName "bin\javaw.exe") } |
            Select-Object -First 1

        if ($null -eq $JreHome) {
            if (Test-Path (Join-Path $JreExtract "bin\javaw.exe")) {
                $JreHome = Get-Item $JreExtract
            }
        }

        if ($null -eq $JreHome) {
            throw "Nao foi possivel localizar o Java extraido."
        }

        if (Test-Path $RuntimeDir) {
            Remove-Item -Path $RuntimeDir -Recurse -Force
        }

        New-Item -ItemType Directory -Force -Path $RuntimeDir | Out-Null
        Copy-Item -Path (Join-Path $JreHome.FullName "*") -Destination $RuntimeDir -Recurse -Force
    }

    $Dependencias = @(
        @{
            Nome = "sqlite-jdbc-3.46.1.0.jar"
            Url  = "https://repo.maven.apache.org/maven2/org/xerial/sqlite-jdbc/3.46.1.0/sqlite-jdbc-3.46.1.0.jar"
        },
        @{
            Nome = "slf4j-api-1.7.36.jar"
            Url  = "https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar"
        },
        @{
            Nome = "slf4j-nop-1.7.36.jar"
            Url  = "https://repo.maven.apache.org/maven2/org/slf4j/slf4j-nop/1.7.36/slf4j-nop-1.7.36.jar"
        },
        @{
            Nome = "bcrypt-0.10.2.jar"
            Url  = "https://repo.maven.apache.org/maven2/at/favre/lib/bcrypt/0.10.2/bcrypt-0.10.2.jar"
        },
        @{
            Nome = "bytes-1.5.0.jar"
            Url  = "https://repo.maven.apache.org/maven2/at/favre/lib/bytes/1.5.0/bytes-1.5.0.jar"
        }
    )

    foreach ($Dependencia in $Dependencias) {
        $Destino = Join-Path $LibDir $Dependencia.Nome

        if (-not (Test-Path $Destino)) {
            Baixar-Arquivo $Dependencia.Url $Destino
        }
    }

    if (-not (Test-Path $JavaExe)) {
        throw "O runtime Java foi preparado, mas java.exe nao foi encontrado."
    }

    if (-not (Test-Path $Marker)) {
        Mensagem "Preparando os usuarios de demonstracao no SQLite..."

        & $JavaExe `
            "-Dfile.encoding=UTF-8" `
            "-Dmoneyshield.database=sqlite" `
            "-Dmoneyshield.sqlite.path=$DbPath" `
            -cp "$AppJar;$LibDir\*" `
            br.com.moneyshield.dev.InicializadorUsuariosTeste

        if ($LASTEXITCODE -ne 0) {
            throw "Falha ao executar o inicializador de usuarios."
        }

        New-Item -ItemType File -Force -Path $Marker | Out-Null
    }

    Mensagem "Validando banco e login da versao portatil..."

    & $JavaExe `
        "-Dfile.encoding=UTF-8" `
        "-Dmoneyshield.database=sqlite" `
        "-Dmoneyshield.sqlite.path=$DbPath" `
        -cp "$AppJar;$LibDir\*" `
        br.com.moneyshield.dev.VerificadorPortatil

    if ($LASTEXITCODE -ne 0) {
        throw "A validacao do banco ou do login portatil falhou."
    }

    Mensagem "MoneyShield portatil preparado com sucesso."
    exit 0
}
catch {
    Write-Host ""
    Write-Host "ERRO AO PREPARAR O MONEYSHIELD PORTATIL" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "Na primeira preparacao e necessario acesso a internet para baixar o Java e as bibliotecas."
    exit 1
}
finally {
    if (Test-Path $TempDir) {
        Remove-Item -Path $TempDir -Recurse -Force -ErrorAction SilentlyContinue
    }
}
