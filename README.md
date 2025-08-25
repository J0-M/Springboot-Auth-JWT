# Autenticação & Login – Spring Boot

## Sobre o Projeto

Este projeto foi desenvolvido como um estudo prático de autenticação e autorização em aplicações web com Spring Boot.
Ele implementa um sistema de login com tokens JWT, controle de usuários e um CRUD de produtos protegido por autenticação.

O projeto foi baseado no disponibilizado em: https://www.youtube.com/watch?v=5w-YCcOjPD0&list=PLNCSWIsR6ADJpKXDybHpXTOnVmxZFQaAE&index=10, pela Dev Fernanda Kipper

O foco foi aprender e aplicar conceitos de:
- Spring Boot (API REST, MVC)
- Spring Security com JWT
- Controle de acesso baseado em roles (ex.: ADMIN, CLIENT)
- Persistência de dados com JPA
- Arquitetura em camadas (Controller, Service, Repository)

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security (com JWT)
- Spring Data JPA
- PostgreSQL
- Maven

## Funcionalidades 
- Registro e autenticação de usuários
- Login com JWT Token
- Perfis de usuário (ADMIN / CLIENT)
- CRUD de produtos protegido por autenticação
- Filtros de segurança e autorização via Spring Security

## Estrutura do Projeto
<pre markdown="1">
autenticacaoLogin-main
 ├── README.md
 └── authLoginBackend
     ├── .gitignore
     ├── .mvn
     │   └── wrapper
     │       └── maven-wrapper.properties
     ├── mvnw
     ├── mvnw.cmd
     ├── pom.xml
     └── src
         ├── main
         │   ├── java
         │   │   └── com
         │   │       └── example
         │   │           └── authLogin
         │   │               ├── AuthLoginApplication.java
         │   │               ├── Controllers
         │   │               │   ├── AuthenticationController.java
         │   │               │   └── ProductController.java
         │   │               ├── Infra
         │   │               │   └── Security
         │   │               │       ├── SecurityConfigurations.java
         │   │               │       ├── SecurityFilter.java
         │   │               │       └── TokenService.java
         │   │               ├── Product
         │   │               │   ├── Product.java
         │   │               │   ├── ProductRepository.java
         │   │               │   ├── ProductRequestDTO.java
         │   │               │   └── ProductResponseDTO.java
         │   │               ├── Services
         │   │               │   └── AuthorizationService.java
         │   │               └── User
         │   │                   ├── AuthenticationDTO.java
         │   │                   ├── LoginResponseDTO.java
         │   │                   └── User.java
         │   └── resources
         │       ├── application.properties
         │       ├── static
         │       └── templates
         └── test
             └── java
                 └── com
                     └── example
                         └── authLogin
                             └── AuthLoginApplicationTests.java
</pre>

## Como Executar?
1. Clone e acesse o Respositório:
`
git clone https://github.com/J0-M/Springboot-Auth-JWT.git
cd autenticacaoLogin-main/authLoginBackend
`

2. Configure o banco de dados no arquivo src/main/resources/application.properties:
`
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
`
3. Execute a aplicação:
`
mvn spring-boot:run
`

4. Acesse a URL:
`
http://localhost:8080
`

## Aprendizados
- Configuração do Spring Security com JWT
- Criação de filtros de autenticação (SecurityFilter)
- Implementação de roles de usuário para autorização
- Boas práticas de organização em camadas

## Próximos Passos
- Adicionar refresh token para prolongar sessão
- Implementar recuperação de senha
- Criar documentação da API com Swagger/OpenAPI
- Adicionar testes unitários mais completos
