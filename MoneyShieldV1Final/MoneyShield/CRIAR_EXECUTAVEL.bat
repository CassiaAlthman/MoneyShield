@echo off
setlocal

cd /d "%~dp0"

title MoneyShield - Criar Atalho

echo.
echo ==========================================
echo        MoneyShield - Criar Atalho
echo ==========================================
echo.

for /f "usebackq delims=" %%D in (`powershell -NoProfile -Command "[Environment]::GetFolderPath('Desktop')"`) do set "DESKTOP=%%D"

if not defined DESKTOP (
    set "DESKTOP=%USERPROFILE%\Desktop"
)

set "LAUNCHER=%~dp0ABRIR_MONEYSHIELD.bat"
set "SHORTCUT=%DESKTOP%\MoneyShield.lnk"
set "ICON=%~dp0packaging\windows\MoneyShield.ico"

echo Criando inicializador...

> "%LAUNCHER%" echo @echo off
>> "%LAUNCHER%" echo cd /d "%%~dp0"
>> "%LAUNCHER%" echo setlocal EnableDelayedExpansion
>> "%LAUNCHER%" echo set "JAR="
>> "%LAUNCHER%" echo.
>> "%LAUNCHER%" echo for %%%%F in ^("target\*.jar"^) do ^(
>> "%LAUNCHER%" echo     echo %%%%~nxF ^| findstr /I /V "original sources javadoc tests" ^>nul
>> "%LAUNCHER%" echo     if not errorlevel 1 set "JAR=%%%%F"
>> "%LAUNCHER%" echo ^)
>> "%LAUNCHER%" echo.
>> "%LAUNCHER%" echo if not defined JAR ^(
>> "%LAUNCHER%" echo     echo ERRO: Nenhum JAR executavel foi encontrado em target.
>> "%LAUNCHER%" echo     pause
>> "%LAUNCHER%" echo     exit /b 1
>> "%LAUNCHER%" echo ^)
>> "%LAUNCHER%" echo.
>> "%LAUNCHER%" echo start "" javaw -jar "!JAR!"
>> "%LAUNCHER%" echo endlocal

echo Criando atalho na Area de Trabalho...

set "MS_LAUNCHER=%LAUNCHER%"
set "MS_SHORTCUT=%SHORTCUT%"
set "MS_PROJECT=%~dp0"
set "MS_ICON=%ICON%"

powershell -NoProfile -ExecutionPolicy Bypass -Command "$ws=New-Object -ComObject WScript.Shell; $s=$ws.CreateShortcut($env:MS_SHORTCUT); $s.TargetPath=$env:MS_LAUNCHER; $s.WorkingDirectory=$env:MS_PROJECT; $s.Description='MoneyShield - Educacao Financeira Gamificada'; if(Test-Path $env:MS_ICON){$s.IconLocation=$env:MS_ICON}; $s.Save()"

if exist "%SHORTCUT%" (
    echo.
    echo ==========================================
    echo            ATALHO CRIADO
    echo ==========================================
    echo.
    echo MoneyShield foi criado na Area de Trabalho.
    echo.
) else (
    echo.
    echo ERRO: Nao foi possivel criar o atalho.
    echo.
)

pause
endlocal