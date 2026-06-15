
# 📖 API de Gestão e Organização de Leituras

Esse repositório tem como finalidade projetar, implementar e apresentar uma API RESTful para resolver um problema do mundo real, de acordo com a entrega do trabalho final da disciplina de Programação para Web II, desenvolvido em grupo pelos alunos:

<div align="center">
    <table>
        <tr>
            <td align="center">
                <a href="https://github.com/DarcsonLanucio">
                    <img src="https://github.com/DarcsonLanucio.png"  width="100px;" style="border-radius: 50%;" />
                    <br/>
                    <sub>Darcson Lanucio</sub>
                </a>
                <br />
            </td>
             <td align="center">
                <a href="https://github.com/SouLuigi">
                    <img src="https://github.com/SouLuigi.png"  width="100px;" style="border-radius: 50%;" />
                    <br/>
                    <sub>Marcos Luigi</sub>
                </a>
                <br />
            </td>
            <td align="center">
                <a href="https://github.com/yasminebelmiro">
                    <img src="https://github.com/yasminebelmiro.png"  width="100px;" style="border-radius: 50%;" />
                    <br/>
                    <sub>Yasmine Belmiro</sub>
                </a>
                <br />
            </td>
        </tr>
    </table>
</div>

Nossa escolha foi criar um sistema para organização, acompanhamento e personalização das leituras dos usuários, promovendo um espaço dinâmico e intuitivo para leitores de todos os níveis. A aplicação ajuda a registrar progressos, definir metas de leitura e consumir dados detalhados das obras via integração com a Google Books API.

---

## 🛠️ Requisitos

### Requisitos para avaliação

Na codificação, os seguintes conceitos foram implementados:

- Arquitetura em camadas ✅
- Conexão com BD: CRUD ✅
- JPA: relacionamentos entre tabelas ✅
- Uso de VO/DTO ✅
- Testes ✅ (Testes unitários cobrindo o modelo de domínio e validações)
- HATEOAS ✅ (Implementado via `RepresentationModelAssembler`)
- Swagger ✅
- Cache ✅ (Configurado com `@Cacheable` e `@CacheEvict`)
- Exemplo do consumo dos recursos da API ✅ (Integração Google Books)

### Requisitos funcionais

| ID    | Requisito                                 | Descrição                                                                                                                                                                           | Classe           | Implementado |
| ----- | ----------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ---------- |
| RF001 | Cadastrar leitor                          | O sistema deve permitir o cadastro de novos leitores, armazenando informações básicas como nome (nickname), email de contato e dados de acesso.                                     | Leitor           | ✅         |
| RF002 | Criar estante                             | O sistema deve permitir que um leitor autenticado crie múltiplas estantes personalizadas (ex: "Lidos 2023", "Ficção Científica"), informando um nome para cada uma.             | Estante          | ✅         |
| RF003 | Adicionar livro a estante                 | O sistema deve permitir que o leitor vincule um livro específico a uma de suas estantes, criando um novo item de estante para acompanhamento.                                       | ItemEstante      | ✅         |
| RF004 | Adicionar registro de leitura             | O sistema deve permitir que o leitor registre seu progresso diário em um livro, informando a data, a página atual alcançada e, opcionalmente, um comentário sobre a leitura.        | RegistroLeitura  | ✅         |
| RF005 | Avaliar um livro                          | O sistema deve permitir que o leitor escreva uma resenha em texto e atribua uma nota numérica a um livro.                                                                           | Resenha          | ✅         |
| RF006 | Criar meta anual                          | O sistema deve permitir que o leitor defina um desafio de leitura para um ano específico, estipulando a quantidade alvo de livros que deseja ler (ex: "Ler 12 livros em 2024").  | MetaAnual        | ✅ Parcialmente concluído        |
| RF007 | Verficar meta atual batida                | O sistema deve verificar automaticamente se a quantidade de livros lidos pelo usuário no ano atingiu ou ultrapassou a quantidade alvo definida na meta anual correspondente.          | MetaAnual        | ✅         |
| RF008 | Calcular progresso geral                  | O sistema deve calcular e exibir o percentual de leitura global do leitor, consolidando as páginas lidas e os status de todos os itens em suas estantes.                            | MetaAnual        | ✅         |
| RF009 | Calcular total de paginas da estante      | O sistema deve calcular o somatório do número total de páginas de todos os livros contidos em uma estante específica.                                                               | Estante          | ✅         |
| RF010 | Calcular dias de leitura                  | O sistema deve calcular a quantidade de dias que o leitor levou para concluir a leitura de um livro, baseando-se no intervalo de datas dos seus registros de leitura.               | ItemEstante      | ✅         |
| RF011 | Atualizar status de leitura               | O sistema deve permitir que o leitor altere o status de um livro na sua estante, transitando livremente entre opções como "Quero ler", "Lendo", "Lido" e "Abandonado".     | ItemEstante      | ✅         |
| RF012 | Calcular media de avaliações de um livro  | O sistema deve calcular automaticamente a nota média global de um livro com base em todas as resenhas submetidas pelos diferentes leitores.                                         | Livro            | ✅         |
| RF013 | Listar ficha tecnica de um livro          | O sistema deve exibir os detalhes completos de um livro, incluindo título, código ISBN, autores, editora, gêneros, número de páginas e data de publicação.                          | Livro            | ✅         |
| RF014 | Atualizar progresso da meta               | Ao marcar um livro como "Lido", o sistema deve incrementar automaticamente a quantidade de livros lidos na meta anual correspondente ao ano vigente do leitor.                     | MetaAnual        | ✅         |
| RF015 | Listar obras de um autor e editora        | O sistema deve permitir a visualização do catálogo completo de livros vinculados a um autor específico ou publicados por uma editora específica.                                    | Autor, Editora   | ❌ (Mapeado no BD, sem endpoint) |
| RF016 | Listar lançantos do ano                   | O sistema deve listar os livros publicados em um determinado ano, permitindo filtrar os resultados do catálogo por autores ou editoras.                                             | Autor, Editora   | ❌ (Mapeado no BD, sem endpoint) |

---

## 📊 Modelagem de Domínio

O sistema foi modelado para refletir a complexidade literária por meio das seguintes classes de domínio (Entidades JPA):
1. **`Leitor`**: Representa o usuário da aplicação.
2. **`Livro`**: Representa a obra literária mapeada via Google Books API.
3. **`Estante`**: Uma coleção customizada de livros pertencente a um Leitor.
4. **`ItemEstante`**: O vínculo entre um Livro e uma Estante, onde o leitor acompanha o `StatusLeitura`.
5. **`RegistroLeitura`**: Cada entrada diária de progresso (páginas lidas e comentários).
6. **`Resenha`**: A avaliação e análise crítica do Leitor sobre o Livro.
7. **`MetaAnual`**: O controle do desafio de livros a serem lidos no ano.
8. **`Autor`**: Modelagem do autor ou autores que escreveram a obra.
9. **`Editora`**: Instituição responsável pela publicação da obra.
10. **`Genero`**: Categoria literária associada ao Livro.

---

## 💻 Solução

### Tecnologias e Integrações
Foi implementada uma API RESTful robusta utilizando as seguintes tecnologias:

* **Linguagem**: Java 21
* **Framework**: Spring Boot 3.4.2
* **Banco de Dados**: PostgreSQL
* **Integração Externa**: Google Books API (utilizando `RestTemplate` com políticas de `Spring Retry` para resiliência).

### Principais Dependências
As ferramentas fundamentais utilizadas no `pom.xml` incluem:

* `spring-boot-starter-web`: Criação dos endpoints REST.
* `spring-boot-starter-data-jpa`: Mapeamento ORM e persistência de dados.
* `spring-boot-starter-hateoas`: Navegabilidade da API (Hypermedia as the Engine of Application State).
* `spring-boot-starter-cache`: Otimização de consultas e redução de requisições externas.
* `spring-retry`: Tratamento de falhas e retentativas nas requisições à Google API.
* `springdoc-openapi-starter-webmvc-ui` (v2.8.5): Geração automática de documentação Swagger.
* `mapstruct` (v1.6.3): Automatização limpa e tipada para conversões entre Objetos de Domínio e DTOs.
* `spring-boot-starter-validation`: Validações nativas de Bean Validation para integridade dos payloads.

---

## 📁 Estrutura de Pastas

A aplicação foi rigorosamente organizada seguindo o padrão de Arquitetura em Camadas para garantir escalabilidade e separação de responsabilidades:

```text
src/main/java/ifgoiano/gestor_leitura_api
 ├── assembler/        # Construtores de Links HATEOAS
 ├── config/           # Configurações gerais (Swagger, Beans do RestTemplate)
 ├── controller/       # Pontos de entrada REST da API
 ├── dto/              # Objetos de Transferência (Request e Response)
 ├── exceptions/       # Exceções customizadas e o GlobalExceptionHandler
 ├── mapper/           # Interfaces MapStruct para mapeamento
 ├── model/            # Entidades do Domínio de Negócio (JPA)
 ├── repository/       # Interfaces de comunicação com o Banco de Dados
 └── service/          # Lógica de Negócios e Integração Externa

```

---

## 🚀 Como Executar

### Pré-requisitos

Certifique-se de ter instalado em sua máquina:

* Java 21
* Maven
* Git
* PostgreSQL

### Passo a Passo

1. **Clone o repositório:**
```bash
git clone https://github.com/yasminebelmiro/gestor-leitura-api.git
cd gestor-leitura-api

```


2. **Configure o Banco de Dados:**
Certifique-se de ter o PostgreSQL rodando. A aplicação criará o banco automaticamente se configurado corretamente.
O projeto buscará os seguintes dados no seu `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestor_leitura_db?createDatabaseIfNotExist=true
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui
spring.jpa.hibernate.ddl-auto=update

```


3. **Chave da API do Google Books:**
O projeto já contém uma propriedade `google.books.api.key` no `application.properties`. Se o limite diário da mesma expirar, substitua por uma chave válida própria do Google Cloud Console.
4. **Compile e baixe as dependências:**
```bash
./mvnw clean install

```


5. **Execute a aplicação:**
```bash
./mvnw spring-boot:run
```


6. **Acesse a documentação (Swagger UI):** Com a aplicação em execução, acesse em seu navegador:
```
🔗[http://localhost:8080/swagger-ui/index.html
```

### 🔑 Como obter a Chave da API do Google Books

Para que a integração com o catálogo de livros funcione corretamente, é necessário ter uma chave de API válida do Google Cloud. Segue os passos abaixo para gerar a tua própria chave:

1. **Aceder ao Google Cloud Console:**
   * Vai a [Google Cloud Console](https://console.cloud.google.com/) e faz login com a tua conta Google.

2. **Criar um Projeto:**
   * No topo da página, clica no menu de seleção de projetos e escolhe **"Novo Projeto"** (New Project).
   * Dá um nome ao projeto (por exemplo, `Gestor-Leitura-API`) e clica em **"Criar"**.

3. **Ativar a API do Google Books:**
   * No menu lateral esquerdo, navega até **APIs e Serviços** > **Biblioteca** (APIs & Services > Library).
   * Na barra de pesquisa, digita `Google Books API`.
   * Clica no resultado correspondente e, em seguida, clica no botão **"Ativar"** (Enable).

4. **Gerar as Credenciais:**
   * Após a ativação, volta ao menu lateral e vai a **APIs e Serviços** > **Credenciais** (Credentials).
   * Clica no botão **"+ Criar Credenciais"** (Create Credentials) no topo da tela e seleciona **"Chave de API"** (API Key).
   * Uma janela pop-up será exibida com a tua nova chave gerada. Copia esse código.

5. **Configurar no Projeto:**
   * Abre o ficheiro `src/main/resources/application.properties` no teu código e substitui o valor da propriedade pela tua chave:
     ```properties
     google.books.api.key=SUA_CHAVE_AQUI
     ```

> ⚠️ **Nota de Segurança:** Recomenda-se não expor publicamente esta chave em repositórios abertos do GitHub. No futuro, podes substituir este valor por uma variável de ambiente.
