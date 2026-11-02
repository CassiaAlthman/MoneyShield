@echo off
setlocal EnableExtensions

cd /d "%~dp0\..\.."
set "ROOT=%CD%"
set "APP_NAME=MoneyShield"
set "APP_VERSION=1.0.0"
set "MAIN_CLASS=br.com.moneyshield.app.EducacaoFinanceiraMain"
set "MAIN_JAR=moneyshield-%APP_VERSION%.jar"
set "BUILD_INPUT=%ROOT%\build\jpackage-input"
set "DIST=%ROOT%\dist"
set "ICON=%ROOT%\packaging\windows\MoneyShield.ico"

call :check_tool java "Java/JDK"
if errorlevel 1 exit /b 1

call :check_tool javac "JDK"
if errorlevel 1 exit /b 1

call :check_tool jpackage "jpackage"
if errorlevel 1 exit /b 1

call :check_tool mvn "Apache Maven"
if errorlevel 1 exit /b 1

if not exist "%ICON%" (
    echo [ERRO] Icone nao encontrado:
    echo        %ICON%
    exit /b 1
)

echo.
echo ==============================================
echo   MoneyShield - Geracao do executavel Windows
echo ==============================================
echo.
echo [1/4] Compilando e gerando JAR executavel...
call mvn clean package -DskipTests
if errorlevel 1 (
    echo.
    echo [ERRO] O Maven nao conseguiu gerar o projeto.
    exit /b 1
)

if not exist "%ROOT%\target\%MAIN_JAR%" (
    echo.
    echo [ERRO] JAR esperado nao encontrado:
    echo        %ROOT%\target\%MAIN_JAR%
    exit /b 1
)

echo.
echo [2/4] Preparando arquivos do jpackage...
if exist "%BUILD_INPUT%" rmdir /s /q "%BUILD_INPUT%"
mkdir "%BUILD_INPUT%" >nul
copy /y "%ROOT%\target\%MAIN_JAR%" "%BUILD_INPUT%\%MAIN_JAR%" >nul

if exist "%DIST%" rmdir /s /q "%DIST%"
mkdir "%DIST%" >nul

echo.
echo [3/4] Criando MoneyShield.exe com runtime Java embutido...
jpackage ^
    --type app-image ^
    --name "%APP_NAME%" ^
    --app-version "%APP_VERSION%" ^
    --vendor "MoneyShield" ^
    --description "Sistema de Educacao Financeira Gamificada" ^
    --input "%BUILD_INPUT%" ^
    --dest "%DIST%" ^
    --main-jar "%MAIN_JAR%" ^
    --main-class "%MAIN_CLASS%" ^
    --icon "%ICON%" ^
    --add-modules java.desktop,java.sql,java.naming,java.logging,java.management,java.security.jgss,java.xml,jdk.crypto.ec,jdk.unsupported ^
    --java-options "-Dfile.encoding=UTF-8"

if errorlevel 1 (
    echo.
    echo [ERRO] O jpackage nao conseguiu criar o executavel.
    exit /b 1
)

if not exist "%DIST%\%APP_NAME%\%APP_NAME%.exe" (
    echo.
    echo [ERRO] MoneyShield.exe nao foi encontrado apos o jpackage.
    exit /b 1
)

echo.
echo [4/4] Preparando pacote de instalacao...
xcopy "%ROOT%\database" "%DIST%\database\" /E /I /Y >nul
xcopy "%ROOT%\docs" "%DIST%\docs\" /E /I /Y >nul
copy /y "%ROOT%\packaging\windows\installer\INSTALAR_MONEYSHIELD.bat" "%DIST%\INSTALAR_MONEYSHIELD.bat" >nul
copy /y "%ROOT%\packaging\windows\installer\DESINSTALAR_MONEYSHIELD.bat" "%DIST%\DESINSTALAR_MONEYSHIELD.bat" >nul

if exist "%BUILD_INPUT%" rmdir /s /q "%BUILD_INPUT%"

if exist "%ROOT%\MoneyShield-Windows-%APP_VERSION%.zip" del /q "%ROOT%\MoneyShield-Windows-%APP_VERSION%.zip"

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "Compress-Archive -Path '%DIST%\*' -DestinationPath '%ROOT%\MoneyShield-Windows-%APP_VERSION%.zip' -Force"

if errorlevel 1 (
    echo.
    echo [AVISO] O executavel foi criado, mas o ZIP final nao pode ser gerado.
)

echo.
echo ==============================================
echo   BUILD CONCLUIDO
echo ==============================================
echo.
echo Executavel:
echo   %DIST%\%APP_NAME%\%APP_NAME%.exe
echo.
echo Instalador simples:
echo   %DIST%\INSTALAR_MONEYSHIELD.bat
echo.
echo Pacote para distribuicao:
echo   %ROOT%\MoneyShield-Windows-%APP_VERSION%.zip
echo.
pause
exit /b 0

:check_tool
where %~1 >nul 2>&1
if errorlevel 1 (
    echo [ERRO] %~2 nao foi encontrado no PATH.
    echo.
    if /I "%~1"=="mvn" (
        echo Instale o Apache Maven e abra novamente o terminal.
    ) else (
        echo Use um JDK 24 ou superior com %~1 disponivel no PATH.
    )
    exit /b 1
)
exit /b 0
