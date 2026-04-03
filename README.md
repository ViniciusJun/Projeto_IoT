# 🏠 Sistema IoT Residencial para Monitoramento de Água, Energia e Gás

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![MQTT](https://img.shields.io/badge/MQTT-Mosquitto-orange.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow.svg)

## 📋 Sobre o Projeto

Este projeto acadêmico (Projeto Integrador V) tem como objetivo desenvolver um **Sistema IoT Residencial** capaz de capturar, analisar e processar dados de consumo de **água, energia elétrica e gás** em uma residência.

O sistema permite:
- ✅ Monitoramento em tempo real dos três recursos
- ✅ Detecção automática de vazamentos e consumos excessivos
- ✅ Dashboard interativo para visualização de dados históricos
- ✅ Autenticação via NFC (simulada) para acesso rápido
- ✅ Alertas preventivos baseados em regras de negócio

### 🎯 Motivação

O desperdício de recursos naturais e os riscos domésticos (vazamentos de gás, sobrecarga elétrica, vazamentos de água) representam problemas significativos. Este projeto propõe uma solução de baixo custo e escalável para mitigar esses problemas, utilizando técnicas de simulação para validação da arquitetura.

---

## 🎯 Objetivos

### Objetivo Geral
> Desenvolver e validar, por meio de simulação, um sistema IoT para monitoramento integrado do consumo de água, energia elétrica e gás em ambiente residencial, fornecendo um dashboard interativo, alertas inteligentes e acesso via NFC.

### Objetivos Específicos

| # | Objetivo | Status |
|---|----------|--------|
| 1 | Projetar arquitetura IoT escalável | ✅ Concluído |
| 2 | Implementar simulador de sensores em Java | ✅ Concluído |
| 3 | Desenvolver backend Spring Boot | 🚧 Em desenvolvimento |
| 4 | Criar regras de detecção de anomalias | ⏳ Pendente |
| 5 | Construir dashboard web | ⏳ Pendente |
| 6 | Integrar autenticação NFC simulada | ⏳ Pendente |

---

## 🛠️ Tecnologias Utilizadas

### Backend e Simulação
| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 17 LTS | Linguagem principal |
| Apache Maven | 3.8+ | Gerenciamento de dependências |
| Spring Boot | 3.x | Framework backend |
| Eclipse Mosquitto | 2.0+ | Broker MQTT |
| Eclipse Paho | 1.2.5 | Cliente MQTT para Java |

### Frontend
| Tecnologia | Finalidade |
|------------|------------|
| HTML5 | Estrutura das páginas |
| CSS3 | Estilização e responsividade |
| JavaScript (ES6) | Interatividade e lógica cliente |
| Chart.js | Visualização de gráficos |
| Socket.IO | Comunicação em tempo real |

### Banco de Dados
| Tecnologia | Finalidade |
|------------|------------|
| InfluxDB | Dados de séries temporais (consumo) |
| H2 Database | Banco em memória para testes |

### Ferramentas de Desenvolvimento
| Ferramenta | Finalidade |
|------------|------------|
| IntelliJ IDEA | IDE de desenvolvimento |
| Git / GitHub | Controle de versão |
| Postman | Teste de APIs REST |
| Docker | Containerização (opcional) |

---

## 📁 Estrutura do Projeto
```text
sistema-iot-residencial/
│
├── .gitignore                     # Arquivos ignorados pelo Git
├── README.md                      # Documentação principal
├── LICENSE                        # Licença MIT
│
├── simulador-sensores/            # Simulador Java dos sensores IoT
│   ├── pom.xml                    # Configuração Maven
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── iot/
│                       └── simulator/
│                           ├── Main.java
│                           ├── sensor/
│                           │   ├── Sensor.java
│                           │   ├── AguaSensor.java
│                           │   ├── EnergiaSensor.java
│                           │   └── GasSensor.java
│                           └── mqtt/
│                               └── MqttPublisher.java
│
├── backend-iot/                   # Backend Spring Boot
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── iot/
│       │   │           └── backend/
│       │   │               ├── BackendApplication.java
│       │   │               ├── controller/
│       │   │               ├── service/
│       │   │               ├── repository/
│       │   │               ├── model/
│       │   │               └── config/
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│
├── dashboard-web/                 # Interface web
│   ├── index.html
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   ├── main.js
│   │   ├── charts.js
│   │   └── mqtt-client.js
│   └── assets/
│       └── images/
│
├── docs/                          # Documentação do PI
│   ├── diagramas/
│   │   ├── arquitetura.png
│   │   ├── fluxo-dados.png
│   │   └── casos-uso.png
│   ├── apendices/
│   │   └── APENDICE_A_Instalacao_Ferramentas.md
│   └── referencias/
│       └── referencias.bib
│
└── scripts/                       # Scripts auxiliares
    ├── start-mosquitto.sh
    ├── check-env.sh
    ├── run-simulator.sh
    └── run-all.sh
```
---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [x] **JDK 17** ou superior
- [x] **Apache Maven** 3.8+
- [x] **Eclipse Mosquitto** (MQTT Broker)
- [x] **Git** (opcional, para clonar)
- [x] **Navegador web** (Chrome, Firefox, Edge)

### Passo a Passo

#### 1. Clonar o Repositório

```bash
git clone https://github.com/SEU_USUARIO/sistema-iot-residencial.git
cd sistema-iot-residencial
```