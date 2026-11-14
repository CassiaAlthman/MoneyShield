@echo off
call "%~dp0packaging\windows\build-app.bat"
if errorlevel 1 exit /b 1
call "%~dp0dist\INSTALAR_MONEYSHIELD.bat"
