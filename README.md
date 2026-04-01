# 📦 Microserviço: Fluxo de Pedido

Este microserviço é o ponto de entrada para a criação de pedidos no ecossistema. Desenvolvido com **Java 21** e **Spring Boot**, ele foca em alta performance, consistência de dados e baixo acoplamento através de uma arquitetura orientada a eventos (EDA).

## 🚀 Tecnologias Utilizadas
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.x
* **Mensageria:** Apache Kafka
* **Persistência:** PostgreSQL / Spring Data JPA
* **Documentação de API:** Swagger/OpenAPI
* **Observabilidade:** Micrometer, Prometheus e Grafana

## 🏗️ Arquitetura e Fluxo de Dados
O serviço segue os princípios de **Clean Code** e **SOLID**, garantindo que a regra de negócio esteja isolada das implementações de infraestrutura.

### Diagrama de Sequência
O fluxo abaixo descreve desde a recepção da requisição via REST até a propagação do evento para o Broker.

```mermaid
sequenceDiagram
    participant C as Cliente
    participant API as PedidoController
    participant S as PedidoService
    participant DB as Banco de Dados
    participant K as Apache Kafka

    C->>API: POST /api/v1/pedidos
    API->>S: processar(PedidoDTO)
    activate S
    S->>DB: salvar(Entity)
    DB-->>S: Confirmação
    S->>K: Publicar evento 'pedido-criado'
    K-->>S: Ack
    S-->>API: PedidoResponse
    deactivate S
    API-->>C: 201 Created

## 🧩 Diagrama de Classes (Arquitetura Interna)

A estrutura do projeto foi desenhada seguindo os princípios de **Inversão de Dependência (DIP)** e **Responsabilidade Única (SRP)**. O diagrama abaixo detalha a interação entre os principais componentes do microserviço:

```mermaid
classDiagram
    class PedidoController {
        -PedidoService service
        +criarPedido(PedidoRequest) ResponseEntity
        +buscarPorId(Long) ResponseEntity
    }

    class PedidoService {
        <<interface>>
        +salvar(Pedido) Pedido
        +notificar(Pedido) void
    }

    class PedidoServiceImpl {
        -PedidoRepository repository
        -KafkaProducer producer
        +salvar(Pedido) Pedido
        +notificar(Pedido) void
    }

    class PedidoRepository {
        <<interface>>
        +save(Pedido) Pedido
        +findById(Long) Optional
    }

    class Pedido {
        +Long id
        +String clienteId
        +BigDecimal valorTotal
        +LocalDateTime dataCriacao
    }

    class KafkaProducer {
        -KafkaTemplate template
        +send(String topic, PedidoEvent event)
    }

    PedidoController --> PedidoService : "Usa"
    PedidoService <|.. PedidoServiceImpl : "Implementa"
    PedidoServiceImpl --> PedidoRepository : "Persiste dados"
    PedidoServiceImpl --> KafkaProducer : "Dispara eventos"
    PedidoRepository ..> Pedido : "Gerencia"