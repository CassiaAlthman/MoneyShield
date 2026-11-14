@echo off
setlocal
cd /d "%~dp0"

echo.
echo ATENCAO: isto apagara os dados gravados na versao portatil.
echo.
set /p CONFIRMA=Digite SIM para restaurar o banco: 
if /I not "%CONFIRMA%"=="SIM" exit /b 0

copy /Y "%~dp0portable\template\moneyshield-template.db" "%~dp0portable\data\moneyshield.db" >nul
if exist "%~dp0portable\data\.usuarios-demo-inicializados" del /q "%~dp0portable\data\.usuarios-demo-inicializados"

echo Banco restaurado.
echo Execute CRIAR_ATALHO.bat novamente para preparar os usuarios de demonstracao.
echo.
pause
endlocal
