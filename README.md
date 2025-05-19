[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/Gsn00/workshop-springboot3-jpa/blob/main/LICENSE)

# Workshop Spring Boot com MongoDB

Este projeto é uma API RESTful desenvolvida com Java, Spring Boot e MongoDB, originalmente baseada no curso Java Completo Programação Orientada a Objetos do professor Nélio Alves.
A API permite o gerenciamento completo de usuários e seus posts, incluindo:

 - Cadastro, busca, atualização e exclusão de usuários

 - Criação e listagem de posts com associação ao autor

 - Comentários vinculados a posts

 - Busca avançada de posts, com filtros por:

   - Texto contido no título, corpo ou comentários

   - Intervalo de datas personalizável (data mínima e máxima)


## Melhorias e customizações feitas

Além da base ensinada no curso, este repositório inclui:

- ✅ Integração com **Swagger** (`springdoc-openapi`) para documentação da API
- ✅ Uso de **Lombok** para reduzir boilerplate no código
- ✅ **Testes automatizados**, cobrindo:
  - Cenários de sucesso (testes para endpoints funcionando corretamente)
  - Cenários de falha (testes para validar tratamento de erros e inputs inválidos)

- ✅ Cobertura de testes com **JaCoCo**
- ✅ Estrutura pronta para rodar facilmente localmente


## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data MongoDB
- Lombok
- Swagger (OpenAPI 3)
- JaCoCo
- JUnit 5
- Postman (testes de API e validação dos endpoints)


## Como rodar o projeto localmente

### Pré-requisitos

- Java 17 instalado
- Maven instalado
- MongoDB local rodando na porta padrão (`mongodb://localhost:27017`)

### Passos para rodar:

1. Clone o repositório:
```bash
git clone https://github.com/Gsn00/workshop-springboot-mongodb.git
cd workshop-springboot-mongodb
```

2. Execute o projeto com Maven:
```
./mvnw spring-boot:run
```

4. Acesse a documentação interativa da API via Swagger:
Abra no navegador:
```
http://localhost:8080/swagger-ui/index.html
```
A interface do Swagger permite testar todos os endpoints diretamente do navegador, sem necessidade de Postman ou outras ferramentas externas.

 - Ao iniciar o projeto, dados iniciais são carregados automaticamente (através do Instantiation).

 - Você pode consultar, cadastrar novos usuários, criar posts e adicionar comentários via Swagger.


## Demonstração

Aqui você pode ver a API funcionando através do Swagger UI, com exemplos práticos de uso de alguns endpoints.

### Users -> findAll
![users-findAll](https://github.com/user-attachments/assets/a87c1840-52d9-4d87-b3e0-08ed2e19a827)

### Users -> findById
![users-findById](https://github.com/user-attachments/assets/997fd52d-cced-4e2e-aec6-7e23c599019c)

### Posts -> findAll e fullsearch
![posts-findAll e fullsearch](https://github.com/user-attachments/assets/2104c80f-55ba-44a0-a545-2263533dcb25)

## Autor

Projeto base desenvolvido no curso **Java Completo Programação Orientada a Objetos** do professor [Nélio Alves](https://github.com/acenelio).

Este repositório foi criado e customizado por [Gabriel Novais](https://github.com/Gsn00), que adicionou testes automatizados, Lombok, Swagger e outras melhorias.

