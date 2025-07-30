# OpenBook

OpenBook é um sistema Java Spring Boot para busca, cadastro e gerenciamento de livros e autores utilizando a API pública [Gutendex](https://gutendex.com/). Permite consultar livros pelo título, listar livros e autores cadastrados, filtrar livros por idioma, além de visualizar estatísticas baseadas nos dados armazenados.

---

## Funcionalidades

- Buscar livro pelo título na API Gutendex e salvar no banco local
- Listar livros e autores cadastrados
- Listar autores vivos em um determinado ano
- Filtrar livros por idioma (inglês, português, espanhol, francês, italiano)
- Exibir top 10 livros mais baixados
- Mostrar estatísticas da base de dados (média, máximo, mínimo e quantidade de downloads)

---

## Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Banco de dados PostgreSQL
- Jakarta Persistence API (JPA)
- Maven (gerenciamento de dependências)
- API externa Gutendex para consulta de livros
