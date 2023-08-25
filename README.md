# Desafio API ServeRest
Este é um projeto com o objetivo de realizar testes automatizados para a API do [ServeRest](https://serverest.dev/).

### Descrição:
Nesse projeto foram desenvolvidos os testes automatizados para a API do [ServeRest](https://serverest.dev/)
usando o Spring Boot, Selenium, JUnit e RestAssured.
Com o objetivo de exercitar e mostrar as minhas habilidades de testes de API.

O [ServeRest](https://serverest.dev/) é uma API REST gratuita que simula uma loja virtual com intuito de servir de
material de estudos de testes de API. Para mais informações sobre a documentação, clique no link do
[ServeRest](https://serverest.dev/).

### Requisitos:

- Java 11 ou superior
- Spring Boot 2.7.15
- Selenium 4.1.0
- WebDriverManager 5.2.1
- JUnit Jupiter 5.8.1
- RestAssured 4.5.1

### Abrindo o projeto:
Certifique-se de ter as versões corretas do Java e das dependências mencionadas instaladas em seu ambiente de
desenvolvimento, e após isso clone este repositório.

### Estrutura:

- src/main/java/br/org/desafioAPI/model/Usuario.java: É o modelo de usuário usado nos testes;
- src/test/java/br/org/desafioAPI/api/testData/UsuarioTestData.java: São os dados de usuários usados nos testes;
- src/test/java/br/org/desafioAPI/api: São os testes automatizados do CRUD da API.

### Como executar os testes:

Primeiro tenha certeza de que todas as dependências estão devidamente instaladas.

Confirmando isso, pode seguir o caminho de: src/test/java/br/org/desafioAPI.

Nesse ponto você poderá escolher por: rodar todos os testes de uma vez executando a pasta "api", ou executar arquivo
por arquivo dentro dessa pasta, onde cada arquivo testa um método do CRUD de usuários.

### Sobre os testes:

Os testes de usuário foram criados da seguinte forma:

- Testes que precisam de um usuário criado começam com um post do usuário, para que possam ser buscados, editados ou
apagados;
- É impresso na tela o ID do usuário, caso tenha sido criado;
- Depois são devidamente testados;
- E por último, nos testes que criaram um usuário, são apagados os usuários criados no início, evitando qualquer
problema de uma propriedade única já estar em uso.

### Pontos interessantes:

Há algumas reações interessantes no [ServeRest](https://serverest.dev/), entre elas:

- Um usuário não pode ter e-mail igual ao de outro, podendo retornar um aviso de e-mail já em uso;
- Caso tente deletar um usuário por um ID inexistente, você receberá um código 200 e uma mensagem de que nenhum
registro foi excluído;
- Ao tentar editar, com o método PUT, um usuário com ID inexistente, será criado um usuário com as propriedades que
foram inseridas e você receberá um código 201 com uma mensagem de cadastro realizado com sucesso.
