@echo off

echo Starting Order Service...
cd order-service
docker compose down

echo.
echo Starting Inventory Service...
cd ..\inventory-service
docker compose down

echo.
echo Starting Product Service...
cd ..\product-service
docker compose down

echo.
echo Starting Api Gateway Service...
cd ..\api-gateway
docker compose down

echo.
echo All services started.
pause