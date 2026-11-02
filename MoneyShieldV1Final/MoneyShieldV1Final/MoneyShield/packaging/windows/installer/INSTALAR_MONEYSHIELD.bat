@echo off
setlocal EnableExtensions

set "SOURCE=%~dp0MoneyShield"
set "INSTALL_ROOT=%LOCALAPPDATA%\Programs\MoneyShield"
set "PROGRAMS_ROOT=%LOCALAPPDATA%\Programs"
set "UNINSTALLER=%PROGRAMS_ROOT%\DESINSTALAR_MONEYSHIELD.bat"

if not exist "%SOURCE%\MoneyShield.exe" (
    echo [ERRO] MoneyShield.exe nao foi encontrado ao lado deste instalador.
    echo.
    echo Mantenha INSTALAR_MONEYSHIELD.bat na mesma pasta da pasta MoneyShield.
    pause
    exit /b 1
)

echo ==============================================
echo   MoneyShield - Instalacao local
echo ==============================================
echo.
echo Destino:
echo   %INSTALL_ROOT%
echo.

if exist "%INSTALL_ROOT%" (
    echo Uma instalacao anterior foi encontrada e sera atualizada.
    taskkill /IM MoneyShield.exe /F >nul 2>&1
    rmdir /s /q "%INSTALL_ROOT%"
)

mkdir "%INSTALL_ROOT%" >nul 2>&1
xcopy "%SOURCE%\*" "%INSTALL_ROOT%\" /E /I /H /Y >nul

if errorlevel 1 (
    echo [ERRO] Nao foi possivel copiar os arquivos do MoneyShield.
    pause
    exit /b 1
)

if exist "%~dp0database" (
    xcopy "%~dp0database" "%INSTALL_ROOT%\database\" /E /I /H /Y >nul
)

if exist "%~dp0docs" (
    xcopy "%~dp0docs" "%INSTALL_ROOT%\docs\" /E /I /H /Y >nul
)

copy /y "%~dp0DESINSTALAR_MONEYSHIELD.bat" "%UNINSTALLER%" >nul

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$w = New-Object -ComObject WScript.Shell;" ^
    "$desktop = [Environment]::GetFolderPath('Desktop');" ^
    "$startMenu = [Environment]::GetFolderPath('Programs');" ^
    "$target = '%INSTALL_ROOT%\MoneyShield.exe';" ^
    "$s = $w.CreateShortcut((Join-Path $desktop 'MoneyShield.lnk'));" ^
    "$s.TargetPath = $target; $s.WorkingDirectory = '%INSTALL_ROOT%'; $s.IconLocation = $target + ',0'; $s.Save();" ^
    "$m = $w.CreateShortcut((Join-Path $startMenu 'MoneyShield.lnk'));" ^
    "$m.TargetPath = $target; $m.WorkingDirectory = '%INSTALL_ROOT%'; $m.IconLocation = $target + ',0'; $m.Save();" ^
    "$u = $w.CreateShortcut((Join-Path $startMenu 'Desinstalar MoneyShield.lnk'));" ^
    "$u.TargetPath = '%UNINSTALLER%'; $u.WorkingDirectory = '%PROGRAMS_ROOT%'; $u.Save();"

if errorlevel 1 (
    echo [AVISO] O programa foi instalado, mas algum atalho pode nao ter sido criado.
)

echo.
echo MoneyShield instalado com sucesso.
echo.
set /p "ABRIR=Deseja abrir o MoneyShield agora? [S/N]: "
if /I "%ABRIR%"=="S" start "" "%INSTALL_ROOT%\MoneyShield.exe"

exit /b 0
