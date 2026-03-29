# Fluxo Pedido - Microserviço de Vendas

Este microserviço é responsável pela criação e gestão de pedidos no ecossistema de e-commerce. Ele atua como o produtor de eventos para o tópico de mensageria, iniciando o fluxo de processamento assíncrono.

## 🚀 Tecnologias e Conceitos
* **Java 21**: Utilizando as últimas funcionalidades da linguagem.
* **Spring Boot 3.x**: Framework base para a construção da API.
* **Project Loom (Virtual Threads)**: Configurado para processamento de alto desempenho e escalabilidade.
* **Apache Kafka**: Utilizado para comunicação assíncrona entre microserviços.
* **PostgreSQL**: Banco de dados relacional para persistência de pedidos.
* **Docker & Docker Compose**: Orquestração do ambiente de infraestrutura (Kafka, Zookeeper, Postgres).

## 🏗️ Arquitetura
O projeto segue os princípios de **Clean Code** e **SOLID**, com separação clara entre as camadas de infraestrutura e domínio.

1. **Controller**: Recebe as requisições HTTP de novos pedidos.
2. **Service**: Contém a lógica de negócio e as regras de validação.
3. **Messaging (Producer)**: Envia o evento `OrderEvent` para o tópico `pedidos-criados` no Kafka.
4. **Repository**: Persiste os dados iniciais do pedido no banco de dados.

## ⚙️ Configuração do Ambiente

### Pré-requisitos
* **openSUSE Leap 15.6** (ou outro ambiente Linux).
* **Docker & Docker Compose** instalados.
* **Java 21** e **Maven**.

### Executando a Infraestrutura
Na raiz do projeto (onde se encontra o `docker-compose.yml`), execute:
```bash
docker-compose up -d
