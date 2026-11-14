@echo off
setlocal EnableExtensions
cd /d "%~dp0"

title MoneyShield - Preparar versao portatil

echo.
echo ==============================================
echo       MoneyShield - Versao Portatil
echo ==============================================
echo.
echo Preparando os componentes necessarios...
echo Na primeira vez pode ser necessario acesso a internet.
echo.

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0PREPARAR_PORTATIL.ps1"
if errorlevel 1 (
    echo.
    pause
    exit /b 1
)

for /f "usebackq delims=" %%D in (`powershell -NoProfile -Command "[Environment]::GetFolderPath('Desktop')"`) do set "DESKTOP=%%D"
if not defined DESKTOP set "DESKTOP=%USERPROFILE%\Desktop"

set "MS_SHORTCUT=%DESKTOP%\MoneyShield.lnk"
set "MS_TARGET=%~dp0ABRIR_MONEYSHIELD.bat"
set "MS_WORKDIR=%~dp0"
set "MS_ICON=%~dp0packaging\windows\MoneyShield.ico"

powershell -NoProfile -ExecutionPolicy Bypass -Command "$ws=New-Object -ComObject WScript.Shell; $s=$ws.CreateShortcut($env:MS_SHORTCUT); $s.TargetPath=$env:MS_TARGET; $s.WorkingDirectory=$env:MS_WORKDIR; $s.Description='MoneyShield - Educacao Financeira Gamificada'; if(Test-Path $env:MS_ICON){$s.IconLocation=$env:MS_ICON}; $s.Save()"

if not exist "%MS_SHORTCUT%" (
    echo.
    echo ERRO: Nao foi possivel criar o atalho na Area de Trabalho.
    echo.
    pause
    exit /b 1
)

echo.
echo ==============================================
echo             MONEYSHIELD PRONTO
echo ==============================================
echo.
echo O atalho MoneyShield foi criado na Area de Trabalho.
echo A versao portatil utiliza SQLite e nao exige SQL Server.
echo.
echo Credenciais de demonstracao:
echo Aluno: Aluno 123 / Exemplo123
echo Admin: ADM123 / Exemplo123
echo.
pause
endlocal
