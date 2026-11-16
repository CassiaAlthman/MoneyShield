@echo off
setlocal EnableExtensions
cd /d "%~dp0"

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0PREPARAR_PORTATIL.ps1" -Silencioso
if errorlevel 1 (
    echo.
    echo Nao foi possivel preparar o MoneyShield portatil.
    pause
    exit /b 1
)

if not exist "%~dp0portable\data\moneyshield.db" (
    echo.
    echo ERRO: Banco SQLite portatil nao encontrado.
    echo Use RESTAURAR_BANCO_PORTATIL.bat ou extraia novamente o pacote.
    echo.
    pause
    exit /b 1
)

start "" "%~dp0runtime\bin\javaw.exe" -Dfile.encoding=UTF-8 -Dmoneyshield.database=sqlite -Dmoneyshield.sqlite.path="%~dp0portable\data\moneyshield.db" -cp "app\MoneyShield.jar;lib\*" br.com.moneyshield.app.EducacaoFinanceiraMain

endlocal
