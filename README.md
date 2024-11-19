# Challenge Rede Ancora

## Descrição
Ancora é um projeto desenvolvido em Java utilizando o framework Spring Boot. Ele é gerenciado pelo Maven, o que facilita a construção, dependências e gerenciamento do ciclo de vida do projeto.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para criação de aplicações Java.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.

## Estrutura do Projeto
- `src/main/java`: Contém o código-fonte da aplicação.
- `src/main/resources`: Contém os arquivos de configuração e recursos estáticos.

## Configuração
Para executar o projeto corretamente, é necessário configurar variáveis de ambiente. O projeto inclui um arquivo `.env.example` com as variáveis de ambiente necessárias para a execução. Certifique-se de criar um arquivo `.env` na raiz do projeto, baseado no modelo fornecido e preenchê-lo com os valores apropriados.

### Variáveis de Ambiente Necessárias
- **CLIENT_ID**: ID do cliente fornecido pelo serviço de autenticação.
- **CLIENT_SECRET**: Segredo do cliente fornecido pelo serviço de autenticação.
- **GRANT_TYPE**: Tipo de grant usado para autenticação, geralmente `client_credentials`.
- **SEARCH_URL**: URL do endpoint da API de busca.
- **AUTH_URL**: URL do endpoint do serviço de autenticação.

Exemplo no arquivo `.env.example`:
```ini
CLIENT_ID=id
CLIENT_SECRET=secret
GRANT_TYPE=client_credentials
SEARCH_URL=https://api.example.com/search
AUTH_URL=https://auth.example.com/token
```
## Como Executar

Certifique-se de ter o Java e o Maven instalados em sua máquina.

1. Clone o repositório:
    ```bash
    git clone https://github.com/Gabriel-Leao/java-challenge-ancora
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd java-challenge-ancora
    ```

3. Execute o projeto utilizando o Maven:
    ```bash
    mvn spring-boot:run
    ```

## Documentação

Para mais informações, consulte a documentação oficial:

- [Documentação do Apache Maven](https://maven.apache.org/)
- [Guia de Referência do Spring Boot](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)

## Contribuição

Para contribuir com o projeto, siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma nova branch:
    ```bash
    git checkout -b minha-feature
    ```
3. Faça as alterações desejadas e commit:
    ```bash
    git commit -m "Minha nova feature"
    ```
4. Envie as alterações para o seu fork:
    ```bash
    git push origin minha-feature
    ```
5. Abra um Pull Request no repositório original.

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
