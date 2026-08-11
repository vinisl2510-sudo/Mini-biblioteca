📚 Mini Biblioteca

Aplicação web fullstack para uma pequena biblioteca/livraria online, desenvolvida com Java, Spring Boot e MySQL. Permite que usuários naveguem por livros, favoritem, comprem (compra simulada) e pesquisem por título, autor ou categoria, enquanto administradores gerenciam o catálogo e acompanham métricas pelo painel.

## Funcionalidades

* Cadastro e login de usuários com senha em hash (`BCryptPasswordEncoder`, Spring Security)
* Catálogo de livros com busca por título/autor/categoria, filtro por abas de categoria e ordenação (nome, menor/maior preço)
* Favoritar / desfavoritar livros
* Compra simulada com controle de estoque (desconta automaticamente, bloqueia se esgotado)
* Perfil do usuário com troca de senha (exige confirmação da senha atual)
* Painel administrativo (`ROLE_ADMIN`) com CRUD completo de livros, incluindo capa via URL
* Dashboard administrativo com métricas agregadas: total de livros/usuários/compras, faturamento, livro mais vendido, livro mais favoritado e alerta de estoque baixo
* Modo claro/escuro persistente (`localStorage`)
* Seed automático de livros de exemplo na primeira execução (`DataSeeder`)

## Tecnologias

* Java 21 + Spring Boot 4.1.0
* Spring Security (autenticação, autorização por papéis, CSRF)
* Spring Data JPA / Hibernate
* MySQL
* Thymeleaf (+ thymeleaf-extras-springsecurity6)
* Maven
* Lombok

## Estrutura

```
├── pom.xml
├── .env.example                      # não versionado (.env real)
├── src/main/java/com/minibiblioteca/
│   ├── MiniBibliotecaApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java       # BCrypt, regras de acesso, CSRF
│   │   └── DataSeeder.java           # popula livros de exemplo
│   ├── model/
│   │   ├── Usuario.java
│   │   ├── Livro.java
│   │   ├── Favorito.java
│   │   └── Compra.java
│   ├── repository/
│   │   ├── UsuarioRepository.java
│   │   ├── LivroRepository.java
│   │   ├── FavoritoRepository.java
│   │   └── CompraRepository.java
│   ├── service/
│   │   ├── UsuarioService.java
│   │   ├── LivroService.java
│   │   ├── FavoritoService.java
│   │   ├── CompraService.java
│   │   └── DashboardService.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── LivroController.java
│   │   ├── FavoritoController.java
│   │   ├── CompraController.java
│   │   ├── UsuarioController.java
│   │   ├── LivroAdminController.java
│   │   └── DashboardController.java
│   └── dto/
│       ├── RegistroDTO.java
│       └── DashboardDTO.java
└── src/main/resources/
    ├── application.yaml
    ├── templates/
    │   ├── fragments/navbar.html
    │   ├── login.html, registro.html, perfil.html
    │   ├── livros.html, livro-detalhe.html
    │   ├── favoritos.html, minhas-compras.html
    │   └── admin/
    │       ├── livros-admin.html
    │       ├── livro-form.html
    │       └── dashboard.html
    └── static/
        ├── css/style.css
        └── js/tema.js
```

## Como rodar

### 1. Pré-requisitos

* Java 21+
* Maven (ou use o `mvnw` incluído no projeto)
* MySQL rodando localmente

### 2. Clone o repositório

```
git clone https://github.com/seu-usuario/mini-biblioteca.git
cd mini-biblioteca
```

### 3. Crie o banco de dados no MySQL

```sql
CREATE DATABASE IF NOT EXISTS mini_biblioteca;
```

### 4. Configure o `.env`

```
cp .env.example .env
```

Edite o `.env` e preencha com as credenciais do seu MySQL local:

```
DB_NAME=mini_biblioteca
DB_USERNAME=root
DB_PASSWORD=sua_senha_aqui
SERVER_PORT=8080
REMEMBER_ME_KEY=uma_string_aleatoria_longa
```

⚠️ **Importante sobre o `.env` neste projeto:** o carregamento automático das variáveis foi configurado através do plugin **EnvFile** do IntelliJ IDEA, não por uma dependência do projeto. Isso significa:

* **Rodando pelo IntelliJ:** instale o plugin [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile) (Settings → Plugins → Marketplace → busque "EnvFile") e, na Run Configuration do projeto, marque "Enable EnvFile" e adicione o arquivo `.env`.
* **Rodando por outra IDE ou pelo terminal** (`mvnw spring-boot:run` ou um `.jar` empacotado): as variáveis do `.env` **não são carregadas automaticamente**. Exporte-as manualmente antes de rodar, por exemplo:
  ```
  export DB_NAME=mini_biblioteca
  export DB_USERNAME=root
  export DB_PASSWORD=sua_senha_aqui
  export SERVER_PORT=8080
  export REMEMBER_ME_KEY=uma_string_aleatoria_longa
  ./mvnw spring-boot:run
  ```
  (No Windows/PowerShell, use `$env:DB_NAME="mini_biblioteca"` no lugar de `export`.)

### 5. Rode a aplicação

Pelo IntelliJ: rode a classe `MiniBibliotecaApplication` (com o EnvFile configurado, como explicado acima).

Ou pelo terminal, após exportar as variáveis:
```
./mvnw spring-boot:run
```

O Hibernate cria as tabelas automaticamente na primeira execução, e o `DataSeeder` popula alguns livros de exemplo.

Acesse em: `http://localhost:8080`

### 6. Criando um usuário administrador

Todo cadastro feito por `/registro` recebe `ROLE_USER` por padrão. Para acessar o Painel Admin e o Dashboard, promova seu usuário manualmente após se cadastrar:

```sql
UPDATE usarios SET role = 'ROLE_ADMIN' WHERE email = 'seu-email@exemplo.com';
```

Depois, faça logout e login novamente (a sessão só atualiza o papel no próximo login).

## Segurança

* Nenhuma credencial fica no código-fonte; tudo vem de variáveis de ambiente (`.env`, fora do Git)
* Senhas nunca são armazenadas em texto puro — hash com `BCryptPasswordEncoder`
* Proteção contra SQL Injection: todas as consultas usam Spring Data JPA (métodos derivados ou `@Query` com parâmetros nomeados), nunca concatenação de strings
* CSRF protegido nos formulários (token automático via Thymeleaf)
* Autorização por papéis: rotas `/admin/**` exigem `ROLE_ADMIN`, demais rotas exigem apenas autenticação

---

Projeto pessoal

Desenvolvido como projeto fullstack para portfólio e aprendizado prático de Spring Boot.
