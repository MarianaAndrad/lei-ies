#   LAb03 - Multi-layer applications with Spring Boot

author: <b> Mariana Andrade </b>

date: <b> 26/10/2022 </b>

## **Acesso a DataBases com Spring Boot**

A API de persistência Java define uma <u>interface padrão para gerir dados em bases de dados relacionais</u>.

*O Spring  Boot facilita a criação de aplicativos CRUD por meio de repositórios CRUD baseados em JPA padrão.* 

O objetivo desta primeira atividade é criar um aplicativo CRUD que permita a criação, leitura, atualização e exclusão de dados de um banco de dados relacional.

> [tutorial](https://www.baeldung.com/spring-boot-crud-thymeleaf) <br>
> [Repositório](https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-crud)<br>

<br>

Para o projeto Spring Boot têm-se de colocar as seguintes dependências:

> **Spring Web** - para construir um aplicativo web <br>
> **Spring Data JPA** - para persistir dados em um banco de dados<br>
> **H2 Database** - para armazenar dados em um banco de dados em memória <br>
> **Thymeleaf** - para renderizar modelos HTML <br>
> **Validation** - para validar dados de entrada <br>

<br>
A arquitetura de camadas é uma abordagem de desenvolvimento de software que separa a aplicação em camadas. Cada camada tem uma responsabilidade específica e as camadas são independentes entre si. A camada de apresentação é responsável por apresentar os dados da camada de negócio. A camada de negócio é responsável por processar os dados e executar as regras de negócio. A camada de dados é responsável por armazenar os dados.
<br>
<br>

###  **Camada de Dados**

#### <u>Camada de Domínio</u>
A camada de domínio contém as entidades que representam os dados que o aplicativo manipula. <br>
Cada entidade que vai ser armazenada com o JPA deve ser anotada com `@Entity`. Neste caso, a classe User é uma entidade JPA e será mapeada para uma tabela no banco de dados. A anotação `@Table` define o nome da tabela. A anotação `@Id` indica que o campo id é a chave primária da tabela. A anotação `@GeneratedValue` define que o valor do campo id é gerado automaticamente, através da estratégia `GenerationType.AUTO`.

> Os marcadores utilizados para anotação são do pacote `javax.persistence`.


```java
@Entity
@Table(name = "users")
public Class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String Name;
    @NotBlank(message = "Email is mandatory")
    private String email;
    // getters and setters
}
```
<br>

> O marcador `@NotBlank` é uma restrição de validação do pacote `javax.validation.constraints`, que é usado para validar os dados de entrada.Ou seja, o campo não pode ser nulo. O atributo message é usado para exibir uma mensagem de erro personalizada.

<br>

#### <u>Camada do Repositório</u>
**O Spring Data JPA nos permite implementar repositórios baseados em JPA (um nome chique para a implementação do padrão DAO) com o mínimo de confusão.**
A camada de repositório contém interfaces que estendem a interface `CrudRepository` do Spring Data JPA. Essas interfaces são usadas para acessar os dados no banco de dados. A interface UserRepository estende a interface `CrudRepository` e define métodos para recuperar dados do banco de dados. O Spring Data JPA fornece implementações para esses métodos.

```java
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
```
 
### **Camada de Controle**

**Graças à camada de abstração que spring-boot-starter-data-jpa coloca no topo da implementação JPA subjacente, podemos adicionar facilmente algumas funcionalidades CRUD ao nosso aplicativo da web por meio de uma camada da web básica.**

A camada de controle é responsável por receber solicitações HTTP e enviar respostas HTTP. 
Para este caso, a classe *UserController* é uma classe de controle que contém métodos para manipular solicitações HTTP, a anotação `@Controller` indica que esta classe é um controlador. 

<br>

>A anotação `@GetMapping` é usada para mapear solicitações HTTP GET em métodos de manipulador específicos.
> A anotação `@PostMapping` é usada para mapear solicitações HTTP POST em métodos de manipulador específicos.
>A anotação `@Autowired` injeta o objeto UserRepository no controlador. O objeto UserRepository é usado para acessar os dados no banco de dados.

<br>

```java
@Controller
public class UserController {    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(user);
        return "redirect:/index";
    }

    // additional CRUD methods
}
```
<br>

### **Camada de Visualização**
A camada de visualização é responsável por renderizar modelos HTML.
Na pasta `src/main/resources/templates`, cria-se os arquivos HTML que contêm o código HTML e Thymeleaf.

O arquivo `add-user.html` contém o formulário para adicionar um novo usuário.

```html
<form action="#" th:action="@{/adduser}" th:object="${user}" method="post">
    <label for="name">Name</label>
    <input type="text" th:field="*{name}" id="name" placeholder="Name">
    <span th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></span>
    <label for="email">Email</label>
    <input type="text" th:field="*{email}" id="email" placeholder="Email">
    <span th:if="${#fields.hasErrors('email')}" th:errors="*{email}"></span>
    <input type="submit" value="Add User">   
</form>
```
<br>

>*Observe como usamos a expressão de URL `@{/adduser}` para especificar o de ação atributo do formulário e as expressões de variável `${}` para incorporar conteúdo dinâmico no modelo, como os valores dos name e email campos e a pós-validação erros.* <br>
> A anotação `th:action` é usada para definir a ação do formulário. <br>
> A anotação `th:object` é usada para vincular o formulário ao objeto User. <br>
> A anotação `th:field` é usada para vincular os campos do formulário aos campos do objeto User. <br>
> A anotação `th:if` é usada para exibir mensagens de erro personalizadas. <br>

<br>

### **Disponibilização da aplicação**
Com uma entidade e um repositório, podemos criar uma aplicação web simples que permite adicionar, visualizar, editar e excluir usuários.
Para o fazer basta criar o ponto de acesso numa classe com a anotação `@SpringBootApplication` e executar o método `main()`.

```java
@SpringBootApplication
public class SpringBootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }

}
```

>Para executar a aplicação, basta executar o método `main()` na classe `SpringBootWebApplication`.<br>
>A aplicação web é executada no endereço `http://localhost:8080/`.

<br>

## **Questions 3_1**

1. A classe *UserController* recebe uma instância de *userRepository* através do seu construtor. Como é que este novo repositório é instanciado?
   
    **R:** A classe *UserController* recebe uma instância de *userRepository* através do seu construtor. O *userRepository* instância tem operações CRUD para manipular os dados no banco de dados. A instância do *userRepository* é injetada no controlador usando a anotação `@Autowired`.


1. Listar os métodos invocados no objecto *userRepository* pelo *UserController*. Onde são definidos estes métodos?
   
    **R:** Os métodos invocados no objecto *userRepository* pelo *UserController* são: `save()`, `findAll()`, `findById()`, `deleteById()`. Estes métodos são definidos na interface *CrudRepository*.
    > Alguns metodos: <br>
    >
    > `findAll(): List<T>` Permite consultar todos os registos na base de dados
    >
    > `save(T entity): T entity` Permite guardar uma entidade na base de dados
    >
    > `findById(ID id): T entity` Permite aceder a uma entidade através do seu ID
    >
    > `delete(T entity)` Permite eliminar uma entidade
    >...


2. Onde é que os dados estão a ser guardados?
   
    **R:** Os dados estão a ser guardados na base de dados H2. Esta é uma base de dados em memória que é criada automaticamente quando a aplicação é executada. Assim, quando  a aplicação é interrompida, os dados são perdidos.
    > Para prevenir este comportamento e assim garantir a persistência dos dados esta dependência deve ser configurada nesse sentido, tal como é descrito na [documentação oficial](http://www.h2database.com/html/features.html#in_memory_databases).

3. Onde é definida a regra para o endereço de correio electrónico *não vazio*?

    **R:** A regra é definida na classe *User* através da anotação `@NotBlank` é usada para validar o campo email, garantindo que não é vazio. 
    No entanto, poderia ter sido outra anomotação, como por exemplo `@NotEmpty` ou `@NotNull`.



## Criar instância local de servidor MySQL com Docker
Para criar uma instância local de servidor MySQL com Docker, basta executar o seguinte comando no terminal:

```bash
$ docker run --name mysql5 -e MYSQL_ROOT_PASSWORD=secret1 -e MYSQL_DATABASE=demo -e MYSQL_USER=demo -e MYSQL_PASSWORD=secret2-p 33060:3306 -d mysql/mysql-server:5.7
```

Uma outra forma de criar a instância é através do ficheiro `docker-compose.yml`:

```yml
services:
  mysql:
    image: mysql/mysql-server:5.7
    environment:
      - MYSQL_ROOT_PASSWORD=secret1
      - MYSQL_PASSWORD=secret2
      - MYSQL_DATABASE=demo
      - MYSQL_USER=demo
    ports:
      - 33060:3306/tcp
```

Configurar projeto para se conectar à base de dados, através do ficheiro `application.properties`. Ver mais [aqui](https://howtodoinjava.com/spring-boot2/datasource-configuration/#configurations).

```properties
# MySQLspring.datasource.url=jdbc:mysql://127.0.0.1:33060/demo
spring.datasource.username=demo
spring.datasource.password=secret2
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

# Strategy to auto updatethe schemas  (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

>**Nota** <br>
> O ficheiro `application.properties` deve ser colocado no diretório `src/main/resources` do projeto.

<br>

## Criar interface REST

> [Tutorial](https://www.javaguides.net/2018/09/spring-boot-2-jpa-mysql-crud-example.html)

> Codigo disponibilizado no [Repositório](https://github.com/RameshMF/spring-boot2-jpa-crud-example)

O objetivo é criar uma API REST para um aplicativo de gerenciamento de funcionários usando Spring Boot 2, Spring Data JPA, MySQL e Maven.

> **Nota:** <br>
> Feito como no exercicio anterior, apenas com dados diferentes.
> Importante ver as secções a [exceções](#-execeçõs) e o [criar queries](#criar-queries) não foram abordados anteriormente.
> 

### <u> **Criar um projeto Maven** </u>

Crie um projeto Maven com o nome `spring-boot2-jpa-crud-example` e adicione as seguintes dependências: 
 1. web;
 2. JPA;
 3. MySQL Driver;
 4. DevTools;
 5. validation.

<br>

### </u>**Criar entidade Employee (Entidades)**</u>

Tal como na aplicação anterior,o atributo que identifica a entidade é o `id`, definido como `@Id` e `@GeneratedValue`.

A entidade ainda será mapeada para a tabela `employee` na base de dados. A classe Employee terá os seguintes campos:

 1. id (chave primária);
 2. firstName;
 3. lastName;
 4. emailId.

```java
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    // Other attributes
    // getters and setters
}
```
<br>

### <u>**Criar interface EmployeeRepository (Gestão de entidades)**</u>
Para gerir as operações de CRUD, é necessário criar uma interface que extende a interface `JpaRepository`. Esta interface fornece métodos para criar, ler, atualizar e excluir (CRUD) operações em uma base de dados.

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
```
A classe estendida pela que criamos, `JpaRepository`, fornece métodos CRUD para a classe `Employee` e o tipo de chave primária `Long`.

>Métodos CRUD fornecidos pela interface `JpaRepository`:
> 1. `save(S entity)` Salva uma entidade e retorna a mesma.
> 2. `saveAll(Iterable<S> entities)` Salva todas as entidades fornecidas e retorna a mesma.
> 3. `findById(ID id)` Retorna uma entidade com o ID fornecido.
> 4. `existsById(ID id)` Retorna `true` se uma entidade com o ID fornecido existir.
> 5. `findAll()` Retorna todas as entidades.
> 6. `findAllById(Iterable<ID> ids)` Retorna todas as entidades com os IDs fornecidos.
> 7. `deleteById(ID id)` Deleta a entidade com o ID fornecido.
> entre outros.

### ** Criar classe EmployeeController (controlador REST)**
Para permitir a gestão do repositório através de pedidos HTTP temos agora de criar a interface REST, através de um controlador  `EmployeeController`, anotado com `@RestController`. 
    
```java
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // Other methods
}
```
<br>

### <u>**Execeçõs**</u>
É preciso implementar mecanismos que deem resposta a casos em são feitas consultas sobre entidades não existentes.

Esta deve ser uma classe que descende de `Exception` e tem anotado o *status* HTTP que retorna através de `@ResponseStatus`.

```java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
```
<br>

### <u>**Criar queries**</u>

[tutorial](https://spring.io/guides/gs/accessing-data-jpa/#_create_simple_queries)

Até aqui só foram configuradas consultas de todos os elementos da base de dados sem filtros. No entanto, a filtragem pelos atributos das entidades é bastante simples e implementada simplesmente através da definição de um método na interface do repositório.

A sua implementação é criada por defeito pela `Spring Data JPA`.

```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{


    List<Employee> findByEmailId(String emailID);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);
}
```
<br>

## Ultima etapa

> **Nota**: só vão ser abordados os conceitos ainda não falados

### <u>**Criar entidades**</u>
Anotação novas utilizadas:

1. `@OneToMany` - indica que a entidade é uma coleção de outras entidades.
2. `@JoinColumn` - indica que a entidade é uma chave estrangeira.
3. `@ManyToOne` - indica que a entidade é uma chave estrangeira.

```java
@Entity
@Table(name = "shows")
public class Show  {

    @Column(name = "source")
    private String source;
    @Column(name = "type")
    private String type;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "show")
    private List<Quote> quotes;
    
    // Other attributes
    // getters and setters
}

@Entity
@Table(name = "quotes")
public class Quote {
    private String quote;
    private String author;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    
    // Other attributes
    // getters and setters
}
```
<br>


### <u>**Service**</u>
A camada de serviço é responsável por implementar a lógica de negócio da aplicação. É aqui que se implementam as regras de negócio e as validações.
Este é um componente que é utilizado por outros componentes da aplicação, como os controladores REST.

```java
@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ShowRepository showRepository;

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(long id) {
        return quoteRepository.findById(id).get();
    }

    /// Other methods
}
```

<br>

## Deploy num Docker Container

> [Tutorial](https://spring.io/guides/topicals/spring-boot-docker/)

```bash
$ docker compose up --build -d
$ docker compose down
$ docker logs nameContainer
```
<br>

# Review questions

1. Explique as diferenças entre os componentes RestController e Controller usados em diferentes
partes deste laboratório. <br>
    **Resposta**: 
    A anotação `@Controller` indica que uma classe é um controlador, que gere pedidos HTTP. Esta classe pode ter métodos anotados com `@RequestMapping` para mapear pedidos HTTP para métodos específicos.
    Abstrai o programador de estender classes base ou de fazer qualquer referência com à API do servlet. <br>
    Contudo, a gestão de pedidos pode retornar vàrios tipos de respostas, entre as quais objetos. Para isso, é necessário que o método retorne um objeto, que será automaticamente convertido para JSON e retornado no corpo da resposta. Para tal basta adicionar a anotação `@ResponseBody` à classe do controller.<br>

    Desta maneira, foram criados duas notações, `@RestController` e `@Controller`. Onde a primeria define controladores RestFull. <br>

2. Create a visualization of the Spring Boot layers (UML diagram or similar), displaying the key
abstractions in the solution of 3.3, in particular: entities, repositories, services and REST controllers.
Describe the role of the elements modeled in the diagram. <br>

    ![Diagrama](/Lab03_3/Ex3Application.png)
    **Resposta**:
    As entidades neste caso são as classes `Quote` e `Show`. Estas são classes que representam as tabelas da base de dados. Estes tem uma relação de **oneToMany**, onde um show pode ter várias quotes. 

   - `Show` - Entidade que representa uma série ou movie, com os atributos `id`, `source`, `type`, List<Quote> `quotes`.
   - `Quote` - Entidade que representa uma citação, com os atributos `id`, `quote`, `author`, `show`. <br><br>
  
    Os repositórios são interfaces que estendem a interface `JpaRepository` e que permitem a comunicação com a base de dados. Estas interfaces são implementadas automaticamente pela `Spring Data JPA`. No exercicio 3.3 foram criados dois repositórios, um para cada entidade. <br>
    - `ShowRepository` - Interface que extende `JpaRepository` e permite a gestão de entidades `Show` na base de dados.
    - `QuoteRepository` - Interface que extende `JpaRepository` e permite a gestão de entidades `Quote` na base de dados.
  
    <br>
    Os serviços são classes que implementam a lógica de negócio da aplicação. Estas classes são utilizadas pelos controladores REST para obter os dados necessários para responder aos pedidos HTTP. Nestes laboratórios foram criados um serviço para as duas entidades. <br>
    - `ApiService` - Classe que implementa a lógica de negócio da entidade `Show` e `Quote`.
  
    <br>
    E por fim os controladores REST são classes que implementam os métodos que respondem aos pedidos HTTP. Estas classes são anotadas com `@RestController` e os métodos com `@RequestMapping`. <br>
    - `ApiController` - Classe que implementa os métodos que respondem aos pedidos HTTP para a entidade `Show` e `Quote`.

    Como página inicial, foi criado um método que retorna uma página HTML com um link para a documentação da API. <br>
    - `IndexController` - Classe que implementa o método que retorna a página inicial da aplicação.
    - `index.html` - Ficheiro HTML que contém a página inicial da aplicação.<br><br>


3. Explain the annotations @Table, @Colum, @Id found in the Employee entity. <br>

    **Resposta**: 
    A tabela onde cada entidade serà guardada é definida através da anotação `@Table`. O nome da tabela é definido através do atributo `name` da anotação. (`@Table(name=<tableName>)`)<br>

    A anotação `@Column` é utilizada para definir o nome da coluna na tabela. (`@Column(name=<columnName>)`), ou seja, onde cada atributo da entidade vai ser mapeado, na tabela.<br>

    O atributo que representa a chave primária é identificado através da anotação `@Id`, e nos casos dados foi gerada automaticamente com `@GenerateValue`, onde pode ser especificado a estratégia de geração. <br><br>



4. Explain the use of the annotation @AutoWired (in the Rest Controller class).<br>
    **Resposta**: A anotação `@AutoWired` é utilizada para injetar dependências. No caso do controlador, é utilizado para injetar o repositório, que é uma dependência do controlador. <br>
   

