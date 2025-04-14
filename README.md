# Challenge Rede Ancora

## Descrição

Ancora é um projeto de backend desenvolvido em Java com o framework Spring Boot. Ele implementa autenticação, cadastro e listagem de usuários utilizando banco de dados PostgreSQL e segue boas práticas de arquitetura, segurança e organização de código.

## Tecnologias Utilizadas

- **Java 17**: Linguagem principal do projeto.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Security**: Para configuração de segurança e autenticação.
- **PostgreSQL**: Banco de dados relacional utilizado.
- **Maven**: Gerenciador de dependências e build.
- **HikariCP**: Gerenciador de conexões com o banco.
- **Dotenv Java**: Para carregamento de variáveis de ambiente.
- **Lombok**: Geração de código boilerplate.
- **JPA (Jakarta Persistence API)**: Para mapeamento objeto-relacional (ORM). *Instalado, mas ainda não em uso.*
- **Flyway**: Para migração de banco de dados. *Instalado, mas ainda não em uso.*
- **JUnit 5**: Framework de testes unitários.
- **Mockito**: Framework de mock para testes.

## Estrutura do Projeto

- `entities/`: Classes que representam as tabelas do banco.
- `dtos/`: Objetos de transferência de dados.
- `configs/`: Configurações de segurança, banco e utilitários.
- `exceptions/`: Exceções customizadas e tratador global.
- `factories/`: Criação de conexão com o banco.
- `utils/`: Funções auxiliares (ex: carregamento de variáveis ambiente).
- `tests/`: Contém os testes unitários.

## Configuração

É necessário criar um arquivo `.env` na raiz do projeto com as configurações de acesso ao banco. Um arquivo `.env.example` está disponível como referência.

### Variáveis de Ambiente Necessárias

```ini
DB_HOST=localhost:5432
DB_NAME=ancora
DB_USER=usuario
DB_PASSWORD=senha
```

## Inicialização do Banco de Dados

A tabela `users` será criada automaticamente na inicialização da aplicação, caso ainda não exista. Essa lógica se encontra em `DatabaseInitializer.java`.

### Estrutura da Tabela

```sql
CREATE TABLE IF NOT EXISTS users (
    id         SERIAL       PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
```

## Endpoints da API

### 1. **GET /users**
**Método**: `GET`  
**Descrição**: Recupera todos os usuários.  
**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
[
    {
        "id": 1,
        "name": "Nome do Usuário",
        "email": "email@exemplo.com"
    }
]
```

### 2. **GET /users/{id}**
**Método**: `GET`  
**Descrição**: Recupera um usuário específico pelo ID.  
**Parâmetros de URL**:
- `id` (Long): ID do usuário que será recuperado.

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
    "id": 1,
    "name": "Nome do Usuário",
    "email": "email@exemplo.com"
}
```

### 3. **POST /users**
**Método**: `POST`  
**Descrição**: Cria um novo usuário.  
**Body** (JSON):
```json
{
    "name": "Nome do Usuário",
    "email": "email@exemplo.com",
    "password": "senha123"
}
```
- **Campos obrigatórios**:
   - `name`: Nome do usuário (mínimo 3 caracteres).
   - `email`: Email do usuário (formato válido).
   - `password`: Senha do usuário (mínimo 8 caracteres).

**Resposta**:
- **Código HTTP**: `201 Created`
- **Body**:
```json
{
    "id": 1,
    "name": "Nome do Usuário",
    "email": "email@exemplo.com"
}
```

### 4. **PUT /users/{id}**
**Método**: `PUT`  
**Descrição**: Atualiza um usuário existente.  
**Parâmetros de URL**:
- `id` (Long): ID do usuário que será atualizado.

**Body** (JSON):
```json
{
    "name": "Novo Nome",
    "email": "novoemail@exemplo.com",
    "password": "novaSenha123"
}
```
- **Campos obrigatórios**:
   - `name`: Nome do usuário (mínimo 3 caracteres).
   - `email`: Email do usuário (formato válido).
   - `password`: Senha do usuário (mínimo 8 caracteres).

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
    "id": 1,
    "name": "Novo Nome",
    "email": "novoemail@exemplo.com"
}
```

### 5. **DELETE /users/{id}**
**Método**: `DELETE`  
**Descrição**: Deleta um usuário existente.  
**Parâmetros de URL**:
- `id` (Long): ID do usuário que será deletado.

**Body** (JSON):
```json
{
    "password": "senha123"
}
```
- **Campos obrigatórios**:
   - `password`: Senha do usuário (para confirmação antes da exclusão).

**Resposta**:
- **Código HTTP**: `204 No Content`
- **Body**: Não há corpo na resposta, indicando que o usuário foi deletado com sucesso.


## Como Executar

1. Clone o repositório:
```bash
  git clone https://github.com/Gabriel-Leao/java-challenge-ancora
```

2. Navegue até o diretório do projeto:
```bash
  cd java-challenge-ancora
```

3. Crie um arquivo `.env` com base no `.env.example` e insira suas configurações.

4. Execute a aplicação com o Maven:
```bash
  mvn spring-boot:run
```

## Documentação

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/)

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Colaboradores do grupo RealTech

<div>
 <p><img src="https://github.com/Projeto-Dev-Aula/cp2-front-web-2sem/assets/145347801/addf3154-41e5-4227-ba6d-887d3ea737a1" alt="Karolina Soares" />
 Karolina Soares - RM 554187</p>

 <p><img src="https://github.com/Projeto-Dev-Aula/cp2-front-web-2sem/assets/145347801/60205ee0-38b2-44f3-bcaa-c1f84b0bdd0d" alt="Thaís Leoncio" />
 Thaís Leoncio - RM 553892</p>

 <p><img src="https://github.com/Projeto-Dev-Aula/cp2-front-web-2sem/assets/145347801/6e0947c2-817e-4c07-9507-36cf683b08f3" alt="André Neves" />
 André Neves - RM 553515</p>

 <p><img src="https://github.com/Projeto-Dev-Aula/cp2-front-web-2sem/assets/145347801/900140b6-2724-452a-a822-296d38ace27f" alt="Eduardo Brites" />
 Eduardo Brites - RM 552943</p>

 <p><img src="https://github.com/Projeto-Dev-Aula/cp2-front-web-2sem/assets/145347801/aa2590ea-10d1-4fbb-82f9-52e05fa5339a" alt="Gabriel Leão" />
 Gabriel Leão - RM 552642</p>
</div>
