# Biblioteca - Spring Boot

Aplicação REST para gerenciamento de livros, autores e categorias utilizando Spring Boot, Spring Data JPA e MySQL. O projeto expõe endpoints CRUD e mantém as relações *muitos-para-um* de livros com autores e categorias.

## O que foi implementado
- **Entidades JPA** `Livro`, `Autor` e `Categoria` com mapeamentos para as tabelas `tbl_livros`, `tbl_autores` e `tbl_categorias` e relacionamentos muitos-para-um (livros possuem um autor e uma categoria). As colunas obrigatórias são marcadas com `nullable = false` e o ISBN do livro é único.
- **Repositórios Spring Data JPA** para cada entidade, permitindo operações CRUD básicas sem código adicional.
- **Serviços** encapsulando a lógica de negócio, validação das referências de autor e categoria, e tratamento de ausência de registros via `ResourceNotFoundException`.
- **Controladores REST** para autores, categorias e livros com endpoints para criar, listar, buscar por ID, atualizar e excluir recursos.
- **Configuração de banco de dados** em `application.properties` apontando para MySQL (com variáveis de ambiente `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD` para customização) e `ddl-auto=update` para criação/atualização de esquema.
- **Carga inicial opcional** em `data.sql` para inserir um autor, uma categoria e um livro de exemplo.

## Estrutura das camadas
- `model/`: classes de entidade JPA com anotações de tabela e relacionamento.
- `repository/`: interfaces que estendem `JpaRepository` para cada entidade.
- `service/`: regras de negócio e validação de vínculos de autor/categoria antes de salvar livros.
- `controller/`: endpoints REST sob `/api/autores`, `/api/categorias` e `/api/livros`.

## Endpoints principais
Abaixo um resumo dos principais endpoints (todos recebem/enviam JSON):

### Autores (`/api/autores`)
- `GET /api/autores` — lista todos os autores.
- `GET /api/autores/{id}` — retorna um autor específico.
- `POST /api/autores` — cria um autor. Exemplo:
  ```json
  { "nome": "J. K. Rowling", "nacionalidade": "Britânica" }
  ```
- `PUT /api/autores/{id}` — atualiza dados do autor.
- `DELETE /api/autores/{id}` — remove o autor informado.

### Categorias (`/api/categorias`)
- `GET /api/categorias` — lista todas as categorias.
- `GET /api/categorias/{id}` — retorna uma categoria específica.
- `POST /api/categorias` — cria uma categoria. Exemplo:
  ```json
  { "nome": "Fantasia" }
  ```
- `PUT /api/categorias/{id}` — atualiza dados da categoria.
- `DELETE /api/categorias/{id}` — remove a categoria informada.

### Livros (`/api/livros`)
- `GET /api/livros` — lista todos os livros.
- `GET /api/livros/{id}` — retorna um livro específico.
- `POST /api/livros` — cria um livro vinculando autor e categoria já existentes. Exemplo:
  ```json
  {
    "titulo": "Dom Casmurro",
    "isbn": "9788503012101",
    "autor": { "id": 1 },
    "categoria": { "id": 1 }
  }
  ```
- `PUT /api/livros/{id}` — atualiza dados do livro, inclusive trocando autor ou categoria.
- `DELETE /api/livros/{id}` — remove o livro informado.

## Configuração do banco de dados
O projeto espera um banco MySQL acessível. Ajuste as variáveis de ambiente conforme necessário:
```
DATABASE_URL=jdbc:mysql://localhost:3306/biblioteca?createDatabaseIfNotExist=true
DATABASE_USERNAME=root
DATABASE_PASSWORD=password
```
O Hibernate está configurado com `spring.jpa.hibernate.ddl-auto=update` para criar/atualizar as tabelas automaticamente. A execução de `data.sql` populada com registros de exemplo pode ser ajustada ou removida conforme o cenário.

## Como executar
1. Configure o banco de dados MySQL e as variáveis de ambiente acima.
2. Instale as dependências e execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
3. A API ficará disponível em `http://localhost:8080`.

## Próximos passos sugeridos
- Adicionar testes automatizados para os serviços e controladores.
- Incluir validação mais robusta de payload (por exemplo, usando Bean Validation).
- Documentar a API com OpenAPI/Swagger para facilitar o consumo.
