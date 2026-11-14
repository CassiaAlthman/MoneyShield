@echo off
setlocal EnableExtensions

set "INSTALL_ROOT=%LOCALAPPDATA%\Programs\MoneyShield"
set "DESKTOP_SHORTCUT=%USERPROFILE%\Desktop\MoneyShield.lnk"
set "START_MENU=%APPDATA%\Microsoft\Windows\Start Menu\Programs"

echo ==============================================
echo   MoneyShield - Desinstalacao
echo ==============================================
echo.
set /p "CONFIRMAR=Remover o MoneyShield deste usuario? [S/N]: "
if /I not "%CONFIRMAR%"=="S" exit /b 0

taskkill /IM MoneyShield.exe /F >nul 2>&1

if exist "%DESKTOP_SHORTCUT%" del /f /q "%DESKTOP_SHORTCUT%"
if exist "%START_MENU%\MoneyShield.lnk" del /f /q "%START_MENU%\MoneyShield.lnk"
if exist "%START_MENU%\Desinstalar MoneyShield.lnk" del /f /q "%START_MENU%\Desinstalar MoneyShield.lnk"
if exist "%INSTALL_ROOT%" rmdir /s /q "%INSTALL_ROOT%"

echo.
echo MoneyShield removido com sucesso.
echo.
pause
exit /b 0
