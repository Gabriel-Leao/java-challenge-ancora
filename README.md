# Challenge Rede Ancora

## Descrição

Ancora é um projeto de backend desenvolvido em Java com o framework Spring Boot. Ele implementa autenticação, cadastro e listagem de usuários utilizando banco de dados H2 em memória e segue boas práticas de arquitetura, segurança e organização de código.

## Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Security**: Para configuração de segurança e autenticação.
- **H2 Database**: Banco de dados em memória utilizado para desenvolvimento e testes.
- **SpringDoc OpenAPI**: Documentação interativa da API (Swagger UI).
- **Maven**: Gerenciador de dependências e build.
- **HikariCP**: Gerenciador de conexões com o banco.
- **Lombok**: Geração de código boilerplate.
- **JPA (Jakarta Persistence API)**: Para mapeamento objeto-relacional (ORM).
- **Flyway**: Para migração de banco de dados.
- **Java-jwt**: Para criação e validaçã̀o do token jwt

## Estrutura do Projeto

- `entities/`: Classes que representam as tabelas do banco.
- `dtos/`: Objetos de transferência de dados.
- `configs/`: Configurações de segurança, banco e utilitários.
- `exceptions/`: Exceções customizadas e tratador global.
- `validations/`: Validações personalizadas para os dtos

## Documentação da API com Swagger

A documentação interativa da API está disponível através do SpringDoc OpenAPI (Swagger UI). Para acessar:

1. Execute a aplicação
2. Acesse: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui.html)

## Banco de Dados H2

O projeto utiliza o banco de dados H2 em memória para facilitar os testes. Para acessar o console do H2:

1. Execute a aplicação
2. Acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
3. Credenciais:
  - Datasource URL: `jdbc:h2:mem:ancora_hub`
  - User Name: `sa`
  - Password: (deixe em branco)

## Inicialização do Banco de Dados

A tabela `users` será criada automaticamente na inicialização da aplicação, caso ainda não exista. Essa lógica se encontra em `DatabaseInitializer.java`.

### Estrutura da Tabela

```sql
CREATE TABLE addresses (
  id            UUID         PRIMARY KEY,
  street        VARCHAR(150) NOT NULL,
  number        VARCHAR(20)  NOT NULL,
  complement    VARCHAR(100),
  city          VARCHAR(100) NOT NULL,
  state         CHAR(2)      NOT NULL,
  postal_code   VARCHAR(8)   NOT NULL
);

CREATE TABLE users (
  id          UUID         PRIMARY KEY,
  name        VARCHAR(150) NOT NULL,
  email       VARCHAR(200) NOT NULL,
  password    VARCHAR(255) NOT NULL,
  birthdate   DATE         NOT NULL,
  role        VARCHAR(10)  NOT NULL,
  address_id  UUID         REFERENCES  addresses(id) ON DELETE SET NULL,
  created_at  TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT UNIQUE_USER_EMAIL        UNIQUE(email),
  CONSTRAINT CHECK_ROLE               CHECK (role IN ('USER', 'ADMIN'))
);

```

## Endpoints da API
Esta API permite a criação, consulta, atualização e exclusão de usuários. Além do endereço relacionado a eles.

> ⚠️ **Todas as requisições abaixo, exceto `POST /users` e `POST /auth/login`, requerem um cabeçalho de autorização:**
>
> `Authorization: Bearer <seu_token_jwt>`
---

## Usuários

---
### 1. **GET /users**
**Método**: `GET`  
**Descrição**: Recupera todos os usuários.  
**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
[
    {
        "id": "uuid-do-usuario",
        "name": "Nome do Usuário",
        "email": "email@exemplo.com",
        "birthdate": "yyyy-mm-dd"
    }
]
```
---

### 2. **GET /users/{id}**
**Método**: `GET`  
**Descrição**: Recupera um usuário específico pelo ID.  
**Parâmetros de URL**:
- `id` (UUID): ID do usuário que será recuperado.

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
    "id": "uuid-do-usuario",
    "name": "Nome do Usuário",
    "email": "email@exemplo.com",
    "birthdate": "yyyy-mm-dd"
}
```
---

### 3. **POST /users**
**Método**: `POST`  
**Descrição**: Cria um novo usuário.  
**Body** (JSON):
```json
{
    "name": "Nome do Usuário",
    "email": "email@exemplo.com",
    "password": "senha123",
    "birthdate": "dd/mm/yyyy"
}
```
- **Campos obrigatórios**:
  - `name`: Nome do usuário (mínimo 3 caracteres).
  - `email`: Email do usuário (formato válido).
  - `password`: Senha do usuário (mínimo 8 caracteres).
  - `birthdate`: Data de nascimento do usuário

**Resposta**:
- **Código HTTP**: `201 Created`
- **Body**:
```json
{
    "id": "uuid-do-usuario",
    "name": "Nome",
    "email": "email@exemplo.com",
    "birthdate": "yyyy-mm-dd",
    "token": "token jwt"
}
```
---

### 4. **PUT /users/{id}**
**Método**: `PUT`  
**Descrição**: Atualiza um usuário existente.  
**Parâmetros de URL**:
- `id` (UUID): ID do usuário que será atualizado.

**Body** (JSON):
```json
{
    "name": "Novo Nome",
    "email": "novoemail@exemplo.com",
    "password": "novaSenha123",
    "birthdate": "dd/mm/yyyy"
}
```
- **Campos obrigatórios**:
  - `name`: Nome do usuário (mínimo 3 caracteres).
  - `email`: Email do usuário (formato válido).
  - `password`: Senha do usuário (mínimo 8 caracteres).
  - `birthdate`: Data de nascimento do usuário

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
    "id": "uuid-do-usuario",
    "name": "Novo Nome",
    "email": "novoemail@exemplo.com",
    "birthdate": "yyyy-mm-dd"
}
```
---

### 5. **PATCH /users/{id}**
**Método**: `PATCH`  
**Descrição**: Atualiza parcialmente os dados de um usuário existente.  
**Parâmetros de URL**:
- `id` (UUID): ID do usuário que será atualizado.

**Body** (JSON):
```json
{
    "name": "Nome Atualizado",
    "email": "novoemail@exemplo.com",
    "password": "novaSenha123",
    "birthdate": "dd/mm/yyyy"
}
```
- **Campos opcionais**:
  - `name`: Nome do usuário (mínimo 3 caracteres).
  - `email`: Email do usuário (formato válido).
  - `password`: Senha do usuário (mínimo 8 caracteres).
  - `birthdate`: Data de nascimento do usuário

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
    "id": "uuid-do-usuario",
    "name": "Nome Atualizado",
    "email": "novoemail@exemplo.com",
    "birthdate": "yyyy-mm-dd"
}
```

### 6. **DELETE /users/{id}**
**Método**: `DELETE`  
**Descrição**: Deleta um usuário existente.  
**Parâmetros de URL**:
- `id` (UUID): ID do usuário que será deletado.

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
- --

## Login

---
### 1. **POST /auth/login**
**Método**: `POST`  
**Descrição**: Realiza o login de um usuário e retorna um token JWT.

**Body** (JSON):
```json
{
  "email": "samuraidediadema@gmail.com",
  "password": "stringst"
}
```
- **Campos obrigatórios**:
  - `email`: Email do usuário (formato válido).
  - `password`: Senha do usuário.

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
  "token": "token-jwt"
}
```
---
## Endereço

---
### 1. **GET /users/{userId}/address**
**Método**: `GET`  
**Descrição**: Retorna o endereço do usuário com base no `userId`.

**Parâmetro de rota**:
- `userId` (UUID): ID do usuário

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
  "id": "a1f7d2e1-2c98-4bc4-915e-3ddf325b72a2",
  "street": "Rua das Palmeiras",
  "number": "123",
  "complement": "Apto 45",
  "city": "São Paulo",
  "state": "SP",
  "postalCode": "12345678"
}
```
---

### 2. **POST /users/{userId}/address**
**Método**: `POST`  
**Descrição**: Cria ou atualiza o endereço do usuário. Caso o usuário ainda não tenha endereço, um novo será criado.

**Parâmetro de rota**:
- `userId` (UUID): ID do usuário

**Body** (JSON):
```json
{
  "street": "Rua das Palmeiras",
  "number": "123",
  "complement": "Apto 45",
  "city": "São Paulo",
  "state": "SP",
  "postalCode": "12345678"
}
```
- **Campos obrigatórios**:
  - `street`, `number`, `city`, `state`, `postalCode`

**Resposta**:
- **Código HTTP**: `201 Created`
- **Body**:
```json
{
  "id": "a1f7d2e1-2c98-4bc4-915e-3ddf325b72a2",
  "street": "Rua das Palmeiras",
  "number": "123",
  "complement": "Apto 45",
  "city": "São Paulo",
  "state": "SP",
  "postalCode": "12345678"
}
```
---

### 3. **PUT /users/{userId}/address**
**Método**: `PUT`  
**Descrição**: Atualiza completamente o endereço do usuário. Todos os campos são obrigatórios.

**Parâmetro de rota**:
- `userId` (UUID): ID do usuário

**Body** (JSON):
```json
{
  "street": "Rua Nova",
  "number": "456B",
  "complement": "Casa dos fundos",
  "city": "Campinas",
  "state": "SP",
  "postalCode": "87654321"
}
```
- **Todos os campos são obrigatórios**

**Resposta**:
- **Código HTTP**: `200 OK`
- **Body**:
```json
{
  "id": "a1f7d2e1-2c98-4bc4-915e-3ddf325b72a2",
  "street": "Rua Nova",
  "number": "456B",
  "complement": "Casa dos fundos",
  "city": "Campinas",
  "state": "SP",
  "postalCode": "87654321"
}
```
---

### 4. **DELETE /users/{userId}/address**
**Método**: `DELETE`  
**Descrição**: Exclui o endereço associado ao usuário.

**Parâmetro de rota**:
- `userId` (UUID): ID do usuário

**Resposta**:
- **Código HTTP**: `204 No Content`
- ---

## Como Executar

1. Clone o repositório:
```bash
  git clone https://github.com/Gabriel-Leao/java-challenge-ancora
```

2. Navegue até o diretório do projeto:
```bash
  cd java-challenge-ancora
```

3. Execute a aplicação com o Maven:
```bash
  mvn spring-boot:run
```

## Documentação

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/)
- [Link para o vídeo apresentação](https://www.youtube.com/watch?v=CpUIVoMXoBQ)

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
