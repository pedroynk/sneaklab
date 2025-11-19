# Sneaklab API

API REST para gerenciamento de usuários, pedidos e pagamentos de um e-commerce de tênis (Sneaklab), desenvolvida em **Java 17** com **Spring Boot 3** e estruturada em **Arquitetura Hexagonal (Ports & Adapters)**.

> ✅ Este projeto é **somente a API** (backend).  
> ✅ Banco de dados em **memória (H2)** para facilitar desenvolvimento e avaliação.  

---

## 🧱 Tecnologias

- **Java 17**
- **Spring Boot 3.5.x**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Security (estrutura pronta para autenticação/roles)
  - Spring Boot Actuator
- **H2 Database** (memória, console web)
- **Lombok** (anotações opcionais em algumas partes)
- **Maven** (build / gestão de dependências)
- **Arquitetura Hexagonal (Ports & Adapters)**

---

## 🏛 Arquitetura Hexagonal

O projeto é organizado em camadas bem definidas:

### 1. Domínio (`br.com.uniceplac.sneaklab.domain`)

Contém as **entidades e regras de negócio** centrais:

- `User` / `UserRole`  
- `Pedido` / `StatusPedido`  
- `Pagamento` / `StatusPagamento` / `TipoPagamento`  
- Outras entidades específicas do negócio de e-commerce.

Essas classes não dependem de frameworks (Spring, JPA etc.).  
O domínio fala a **linguagem de negócio** (cliente, pedido, pagamento, status).

---

### 2. Application (Use Cases)  
`br.com.uniceplac.sneaklab.application`

#### Ports IN (`application.ports.in`)

Definem os **casos de uso** expostos para o mundo externo (controladores, mensageria, etc.):

- `GerenciarUsuarioUseCase`
- `GerenciarPedidoUseCase`
- `GerenciarPagamentoUseCase`
- Outros use cases que forem sendo adicionados.

#### Ports OUT (`application.ports.out`)

Definem contratos para depender de infraestrutura sem conhecê-la diretamente:

- `UserRepositoryPort`
- `PedidoRepositoryPort`
- `PagamentoRepositoryPort`
- `NotifierPort` (envio de notificações – e-mail/SMS/etc.)

#### Services (`application.service`)

Implementam os **casos de uso**:

- `UsuarioService`
  - CRUD de usuários.
  - Validações de campos obrigatórios.
  - Verificação de e-mail já existente.
  - Dispara **notificação de boas-vindas** na criação do usuário.
- `PedidoService`
  - Criação de pedido em status `RASCUNHO`.
  - Atualização de status (`RASCUNHO`, `PAGO`, `ENVIADO`, `ENTREGUE`, `CANCELADO`).
  - Integração com `NotificationService` para notificar mudanças de status.
- `PagamentoService`
  - Registro de pagamentos (`PENDENTE`).
  - Aprovação de pagamento (`APROVADO`) com reflexo em `Pedido` (`PAGO`).
  - Estorno de pagamento (`ESTORNADO`) com reflexo em `Pedido` (`CANCELADO`).
  - Integração com `NotificationService` para notificar aprovação e estorno.
- `NotificationService`
  - Traduz eventos de domínio em mensagens de notificação.
  - Usa `NotifierPort` para enviar mensagens sem conhecer a tecnologia concreta.

---

### 3. Adapters (`br.com.uniceplac.sneaklab.adapters`)

Camada de **integração com o “mundo de fora”**.

#### Controllers (`adapters.controllers`)

Expondo API REST (camada HTTP):

- `UsuarioController`
  - Base path: `/api/usuarios`
  - Endpoints:
    - `POST   /api/usuarios` – cria usuário
    - `GET    /api/usuarios` – lista usuários
    - `GET    /api/usuarios/{id}` – busca usuário por ID
    - `PUT    /api/usuarios/{id}` – atualiza usuário
    - `DELETE /api/usuarios/{id}` – exclui usuário

> Controllers de `Pedido` e `Pagamento` seguem o mesmo padrão (REST), respeitando as regras de negócio definidas nos services.

#### DTOs (`adapters.dtos`)

Objetos de transporte específicos de entrada/saída HTTP:

- `UsuarioDto`
  - Converte de/para `User` (domínio).
  - Evita vazar a entidade de domínio diretamente na API.

#### Persistência JPA (`adapters.persistence.jpa`)

Implementações concretas de `*_RepositoryPort` usando Spring Data JPA:

- `UserEntity`
  - Mapeia para tabela `users` no H2.
- `UserSpringDataRepository`
  - Interface `JpaRepository<UserEntity, Long>`.
- `UserRepositoryAdapter`
  - Implementa `UserRepositoryPort` usando `UserSpringDataRepository`.
  - Faz o mapeamento **Entity ↔ Domain**.

Analogamente, a camada de persistência de `Pedido` e `Pagamento` é implementada com o mesmo padrão (entidade JPA + Spring Data repository + adapter).

#### Notificações (`adapters.notifications`)

- `LoggingNotifierAdapter`  
  Implementa `NotifierPort`, simulando o envio de notificações via **log**:

  - Centraliza a saída de notificações em formato:
    - **Destinatário**
    - **Assunto**
    - **Mensagem**

No futuro, pode ser substituído ou complementado por um adapter real (e-mail, SMS, gateway externo) sem alterar `NotificationService` ou os services de domínio.

---

## 🔄 Fluxos de negócio principais

### Usuário

- Criação de usuário com:
  - `name`, `email`, `passwordHash`, `role (ADMIN/CLIENTE/VENDEDOR)`
- Valida se:
  - Nome, e-mail, senha e role são obrigatórios.
  - E-mail não está em uso.
- Após criar:
  - Dispara notificação de boas-vindas via `NotificationService`.

### Pedido

- Criação:
  - Status inicial: `RASCUNHO`.
- Atualização de status:
  - `RASCUNHO → PAGO` (quando pagamento aprovado).
  - `PAGO → ENVIADO → ENTREGUE`.
  - `Qualquer → CANCELADO` (respeitando regras de negócio).
- A cada mudança relevante de status:
  - `NotificationService.notificarStatusPedido(...)` é chamado para avisar o cliente.

### Pagamento

- Criação / registro:
  - Cria pagamento **PENDENTE** vinculado a um `Pedido`.
- Aprovação:
  - `StatusPagamento` → `APROVADO`.
  - Atualiza `Pedido` → `PAGO` (se estava `RASCUNHO`).
  - Chama:
    - `NotificationService.notificarPagamentoAprovado(...)`
    - `NotificationService.notificarStatusPedido(...)`
- Estorno:
  - `StatusPagamento` → `ESTORNADO`.
  - Atualiza `Pedido` → `CANCELADO`.
  - Chama:
    - `NotificationService.notificarPagamentoEstornado(...)`
    - `NotificationService.notificarStatusPedido(...)`

---

## 🗃 Banco de Dados (H2)

O projeto utiliza **H2 em memória**, com configuração típica no `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:sneaklab;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

📘 Dicas de Uso
🔹 Console H2

Console H2 disponível em:
http://localhost:8080/h2-console

JDBC URL:
jdbc:h2:mem:sneaklab

Usuário:
sa

Senha:
(em branco)

🧪 Testes via Postman

Arquivo disponível:
Sneaklab API.postman_collection.json

🚀 Como importar no Postman

Abra o Postman

Clique em Import

Opção A — Arquivo

Aba Files

Upload Files

Selecione Sneaklab API.postman_collection.json

Import

Opção B — Texto

Aba Raw text

Copie o JSON completo

Cole na caixa

Continue → Import
