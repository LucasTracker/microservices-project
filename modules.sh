#!/bin/bash

echo "Iniciando todos os microserviços..."

# Inicia cada serviço em background
./mvnw -pl erudio-config-server spring-boot:run &
PID1=$!
./mvnw -pl greeting-service spring-boot:run &
PID2=$!
./mvnw -pl cambio-service spring-boot:run &
PID3=$!
./mvnw -pl book-service spring-boot:run &
PID4=$!

# Espera todos terminarem (Ctrl+C para parar)
trap "echo 'Encerrando...'; kill $PID1 $PID2 $PID3 $PID4" SIGINT
wait $PID1 $PID2 $PID3 $PID4
