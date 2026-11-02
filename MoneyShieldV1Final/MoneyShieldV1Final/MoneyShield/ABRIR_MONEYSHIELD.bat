@echo off
cd /d "%~dp0"
setlocal EnableDelayedExpansion
set "JAR="

for %%F in ("target\*.jar") do (
    echo %%~nxF | findstr /I /V "original sources javadoc tests" >nul
    if not errorlevel 1 set "JAR=%%F"
)

if not defined JAR (
    echo ERRO: Nenhum JAR executavel foi encontrado em target.
    pause
    exit /b 1
)

start "" javaw -jar "!JAR!"
endlocal
