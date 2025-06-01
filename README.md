# Sistema de Controle de Estoque - API REST

API REST desenvolvida em Spring Boot para gerenciamento de estoque, com autenticação JWT e documentação Swagger.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Security com JWT
- Spring Data JPA
- H2 Database
- Flyway para Migrations
- MapStruct para mapeamento DTO-Entity
- Swagger/OpenAPI para documentação
- Lombok

## 📋 Funcionalidades

### 1. Autenticação e Autorização

O sistema utiliza autenticação baseada em JWT (JSON Web Token) com os seguintes endpoints:

#### 🔐 Autenticação (`/api/auth`)

- **POST /api/auth/login**

  ```json
  {
    "email": "seu@email.com",
    "senha": "suaSenha"
  }
  ```

  Retorna um token JWT para autenticação.

- **POST /api/auth/register**
  ```json
  {
    "nome": "Seu Nome",
    "email": "seu@email.com",
    "senha": "suaSenha"
  }
  ```
  Registra um novo usuário e retorna um token JWT.

### 2. Gestão de Produtos

#### 📦 Produtos (`/api/produtos`)

- **GET /** - Lista todos os produtos
- **GET /{id}** - Busca produto por ID
- **GET /codigo/{codigo}** - Busca produto por código
- **POST /** - Cria novo produto
  ```json
  {
    "codigo": "PROD001",
    "descricao": "Descrição do Produto",
    "tipoProdutoId": 1,
    "fornecedorId": 1,
    "valorFornecedor": 100.0,
    "quantidadeEstoque": 50,
    "ativo": true
  }
  ```
- **PUT /{id}** - Atualiza produto existente
- **DELETE /{id}** - Remove produto
- **GET /tipo/{tipoId}** - Lista produtos por tipo
- **GET /estoque-baixo?quantidadeMinima={quantidade}** - Lista produtos com estoque baixo
- **GET /tipo/{tipoId}/estoque** - Lista produtos por tipo com quantidade de saída e disponível
- **GET /lucro** - Consulta lucro por produto, mostrando quantidade total de saída e lucro total

### 3. Movimentações de Estoque

#### 📊 Movimentações (`/api/movimentos`)

- **GET /** - Lista todas as movimentações
- **GET /{id}** - Busca movimentação por ID
- **GET /produto/{produtoId}** - Lista movimentações de um produto
- **GET /periodo** - Busca movimentações por período
  - Parâmetros:
    - inicio (yyyy-MM-ddTHH:mm:ss)
    - fim (yyyy-MM-ddTHH:mm:ss)
- **POST /** - Registra nova movimentação
  ```json
  {
    "produtoId": 1,
    "tipoMovimentacao": "ENTRADA", // ou "SAIDA"
    "quantidadeMovimentada": 10,
    "valorVenda": 150.0, // obrigatório para SAIDA
    "dataMovimentacao": "2025-06-01T16:14:14"
  }
  ```

### 4. Tipos de Produto

#### 📝 Tipos de Produto (`/api/tipos-produto`)

- **GET /** - Lista todos os tipos
- **GET /{id}** - Busca tipo por ID
- **POST /** - Cria novo tipo
  ```json
  {
    "nome": "Nome do Tipo",
    "descricao": "Descrição do Tipo"
  }
  ```
- **PUT /{id}** - Atualiza tipo existente
- **DELETE /{id}** - Remove tipo

### 5. Fornecedores

#### 🏢 Fornecedores (`/api/fornecedores`)

- **GET /** - Lista todos os fornecedores
- **GET /{id}** - Busca fornecedor por ID
- **POST /** - Cadastra novo fornecedor
  ```json
  {
    "nome": "Nome do Fornecedor",
    "cnpj": "12.345.678/0001-90",
    "email": "fornecedor@email.com",
    "telefone": "(11) 99999-9999"
  }
  ```
- **PUT /{id}** - Atualiza fornecedor
- **DELETE /{id}** - Remove fornecedor

### 6. Endpoints Analíticos

#### 📊 Análises

- **GET /api/produtos/tipo/{tipoId}/estoque** - Consulta de produtos por tipo

  - Retorna:
    - Quantidade de saída
    - Quantidade disponível em estoque
    - Informações do produto

- **GET /api/produtos/lucro** - Consulta de lucro por produto
  - Retorna:
    - Quantidade total de saída
    - Valor do fornecedor (custo)
    - Valor médio de venda
    - Lucro unitário médio
    - Lucro total

## 🔒 Segurança

- Autenticação via JWT Token
- Endpoints protegidos requerem token no header:
  ```
  Authorization: Bearer {seu-token-jwt}
  ```
- Senhas são armazenadas com hash BCrypt
- CORS configurado para permitir requisições de qualquer origem
- Proteção contra CSRF desativada para APIs REST

## 🚦 Tratamento de Erros

Todas as respostas de erro seguem o formato padrão:

```json
{
  "message": "Mensagem descritiva do erro"
}
```

Códigos de status HTTP:

- 200: Sucesso
- 400: Erro de validação/dados inválidos
- 401: Não autorizado
- 404: Recurso não encontrado
- 500: Erro interno do servidor

## 📚 Documentação

A documentação completa da API está disponível através do Swagger UI:

- URL: `/swagger-ui.html`
- Documentação OpenAPI: `/v3/api-docs`

## 🔧 Configuração e Instalação

1. Clone o repositório
2. Configure as variáveis de ambiente (ou application.properties):

   ```properties
   # Server
   server.port=8080

   # Database
   spring.datasource.url=jdbc:h2:mem:estoque-db
   spring.datasource.username=sa
   spring.datasource.password=

   # JWT
   app.jwt.secret=SuaChaveSecreta
   app.jwt.expiration=86400000
   ```

3. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

## 🗃️ Banco de Dados

- Utiliza H2 Database em memória
- Migrations gerenciadas pelo Flyway
- Console H2 disponível em: `/h2-console`
- Dados de conexão padrão:
  - JDBC URL: `jdbc:h2:mem:estoque-db`
  - User: `sa`
  - Password: ` ` (vazio)

## 📈 Monitoramento

- Endpoints de saúde disponíveis em `/actuator`
- Métricas e status da aplicação
- Informações sobre o ambiente

## 🚀 Implementações Futuras

As seguintes funcionalidades foram identificadas como melhorias potenciais para o sistema, mas não foram implementadas na versão atual para manter o foco nos requisitos essenciais:

### Segurança e Controle de Acesso

- Implementação de área administrativa exclusiva com dashboard e relatórios gerenciais
- Refinamento do controle de acesso baseado em papéis (RBAC)
- Registro de log de ações (audit trail) para todas as operações críticas
- Implementação de autenticação em dois fatores (2FA)

### Performance e Usabilidade

- Paginação e filtros avançados em todas as listagens
- Cache de segundo nível para otimização de consultas frequentes
- Implementação de busca fulltext para produtos e movimentações
- Exportação de relatórios em diferentes formatos (PDF, Excel, CSV)

### Funcionalidades de Negócio

- Sistema de alertas para produtos com estoque baixo
- Previsão de demanda baseada no histórico de movimentações
- Gestão de múltiplos depósitos/locais de estoque
- Integração com sistemas de fornecedores para automatizar pedidos
- Módulo de gestão de devoluções e produtos danificados
- Dashboard com indicadores de performance (KPIs)

### Integrações

- Implementação de webhooks para notificações em tempo real
- Integração com sistemas de e-commerce
- API para integração com sistemas de PDV
- Integração com serviços de notificação (email, SMS, push)

### Infraestrutura

- Configuração de ambiente de homologação
- Pipeline de CI/CD completo
- Monitoramento avançado com ELK Stack
- Backup automático do banco de dados
- Containerização com Docker e orquestração com Kubernetes

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✨ Agradecimentos

- Spring Framework Team
- H2 Database
- Todos os contribuidores do projeto

## 🧪 Testes Automatizados

O projeto inclui testes automatizados para garantir a qualidade e confiabilidade do código. Os testes são implementados usando:

- JUnit 5 para testes unitários
- Mockito para simulação de dependências
- AssertJ para asserções fluentes
- H2 Database para testes de integração

### Cobertura de Testes

Os testes cobrem as camadas de serviço da aplicação:

#### 1. Testes de Serviço

- `ProdutoServiceTest`: Testes completos para operações CRUD e regras de negócio
- `MovimentoEstoqueServiceTest`: Testes de movimentações de estoque
- `TipoProdutoServiceTest`: Testes de gerenciamento de tipos de produto
- `FornecedorServiceTest`: Testes de gerenciamento de fornecedores
- `AuthServiceTest`: Testes de autenticação e autorização

### Executando os Testes

Para executar os testes, use o comando:

```bash
./gradlew test
```
