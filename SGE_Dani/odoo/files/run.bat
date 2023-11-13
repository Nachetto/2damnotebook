@echo off
echo Starting Docker containers with docker-compose...
cd C:\Users\Usuario\Documents\GitHub\2damnotebook\SGE_Dani\odoo\files
docker-compose up -d

echo Waiting for Odoo to be ready...
:waitloop
timeout /t 5
curl -s http://localhost:8069 >nul
if errorlevel 1 goto waitloop

echo Opening localhost on port 8069...
start http://localhost:8069
