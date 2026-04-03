#!/bin/bash

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Iniciando Simulador IoT ===${NC}"

# Verifica se o Mosquitto está rodando
if ! pgrep -x "mosquitto" > /dev/null; then
    echo -e "${YELLOW}⚠️ Mosquitto não está rodando. Inicie com: mosquitto -v${NC}"
    exit 1
fi

# Executa o simulador
cd ~/projetos-iot/sistema-iot-residencial/simulador-sensores
mvn exec:java