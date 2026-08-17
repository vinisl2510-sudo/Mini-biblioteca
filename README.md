📚 Mini Biblioteca

Aplicação web fullstack para uma pequena biblioteca/livraria online, desenvolvida com Java, Spring Boot e MySQL. Permite que usuários naveguem por livros, favoritem, comprem (compra simulada, com quantidade e comprovante), avaliem e pesquisem por título, autor ou categoria, enquanto administradores gerenciam o catálogo e acompanham métricas pelo painel.

## Funcionalidades

**Conta e autenticação**
* Cadastro e login de usuários com senha em hash (`BCryptPasswordEncoder`, Spring Security)
* Confirmação de senha no cadastro (validação no servidor) e no login (validação em JavaScript)
* Recuperação de senha por token com expiração de 30 minutos — o link é impresso no console da aplicação, simulando o envio por e-mail sem depender de um servidor SMTP
* Perfil do usuário com troca de senha (exige confirmação da senha atual)

**Catálogo e compra**
* Busca por título/autor/categoria, filtro por abas de categoria e ordenação (nome, menor/maior preço)
* Favoritar / desfavoritar livros
* Compra simulada com quantidade configurável e controle de estoque (desconta automaticamente, bloqueia se insuficiente)
* Comprovante de compra individual, acessível apenas pelo próprio usuário que comprou
* Avaliações (nota de 1 a 5 + comentário) — só quem já comprou o livro pode avaliar, uma avaliação por usuário por livro

**Administração**
* Painel administrativo (`ROLE_ADMIN`) com CRUD completo de livros, incluindo capa via URL
* Exclusão de livros em cascata: remove automaticamente favoritos, compras e avaliações relacionadas antes de excluir o livro
* Dashboard com métricas agregadas: total de livros/usuários/compras, faturamento, livro mais vendido, mais favoritado, melhor e pior avaliado, e alerta de estoque baixo

**Outros**
* Modo claro/escuro persistente (`localStorage`)
* Telas de formulário (login, registro, perfil, admin) centralizadas visualmente
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
│   │   ├── Compra.java               # inclui quantidade e valor total
│   │   ├── Avaliacao.java            # nota + comentário, único por usuário/livro
│   │   └── TokenRecuperacao.java     # token + expiração p/ recuperação de senha
│   ├── repository/
│   │   ├── UsuarioRepository.java
│   │   ├── LivroRepository.java
│   │   ├── FavoritoRepository.java
│   │   ├── CompraRepository.java
│   │   ├── AvaliacaoRepository.java
│   │   └── TokenRecuperacaoRepository.java
│   ├── service/
│   │   ├── UsuarioService.java
│   │   ├── LivroService.java         # inclui cascade delete
│   │   ├── FavoritoService.java
│   │   ├── CompraService.java        # controle de estoque por quantidade
│   │   ├── AvaliacaoService.java
│   │   ├── DashboardService.java
│   │   └── RecuperacaoSenhaService.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── LivroController.java
│   │   ├── FavoritoController.java
│   │   ├── CompraController.java     # inclui rota do comprovante
│   │   ├── AvaliacaoController.java
│   │   ├── UsuarioController.java
│   │   ├── LivroAdminController.java
│   │   ├── DashboardController.java
│   │   └── RecuperacaoSenhaController.java
│   └── dto/
│       ├── RegistroDTO.java          # inclui confirmação de senha
│       └── DashboardDTO.java
└── src/main/resources/
    ├── application.yaml
    ├── templates/
    │   ├── fragments/navbar.html
    │   ├── login.html, registro.html, perfil.html
    │   ├── esqueci-senha.html, redefinir-senha.html
    │   ├── livros.html, livro-detalhe.html
    │   ├── favoritos.html, minhas-compras.html, comprovante.html
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

### 7. Testando a troca/recuperação de senha

**Trocar a senha estando logado:** acesse `/perfil`, informe a senha atual e a nova senha. A troca só é aceita se a senha atual informada bater com o hash salvo no banco.

**Recuperar a senha esquecida:**
1. Na tela de login, clique em "Esqueci minha senha" (ou acesse `/esqueci-senha`) e informe o e-mail cadastrado.
2. Como este projeto não está integrado a um servidor de e-mail real, o link de recuperação é impresso diretamente **no console/terminal onde a aplicação está rodando**, dentro de um bloco destacado com `====`. Procure uma linha assim:
   ```
   http://localhost:8080/redefinir-senha?token=<token gerado>
   ```
3. Copie essa URL e cole no navegador. Isso abre a tela de redefinição, onde você define a nova senha.
4. O link expira em 30 minutos e só pode ser usado uma vez — depois de redefinir a senha, o token é apagado do banco.
5. Por segurança, a tela de "Esqueci minha senha" sempre mostra a mesma mensagem de sucesso, informe um e-mail cadastrado ou não — isso evita que alguém descubra quais e-mails têm conta no sistema apenas testando a funcionalidade.

## Segurança

* Nenhuma credencial fica no código-fonte; tudo vem de variáveis de ambiente (`.env`, fora do Git)
* Senhas nunca são armazenadas em texto puro — hash com `BCryptPasswordEncoder`
* Proteção contra SQL Injection: todas as consultas usam Spring Data JPA (métodos derivados ou `@Query` com parâmetros nomeados), nunca concatenação de strings
* CSRF protegido nos formulários (token automático via Thymeleaf)
* Autorização por papéis: rotas `/admin/**` exigem `ROLE_ADMIN`, demais rotas exigem apenas autenticação
* Tokens de recuperação de senha gerados com `SecureRandom` (aleatoriedade criptograficamente segura), com expiração e uso único
* Comprovantes de compra só são acessíveis pelo próprio usuário que realizou a compra (busca sempre filtrada por `id` + usuário autenticado)
* Exclusão de livros em cascata evita erros de integridade referencial e mantém o banco consistente

---

Projeto pessoal

Desenvolvido como projeto fullstack para portfólio e aprendizado prático de Spring Boot.
