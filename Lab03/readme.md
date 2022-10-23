#   LAb03 - Multi-layer applications with Spring Boot

author: <b> Mariana Andrade </b>

date: <b> --/10/2022 </b>

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

###  **Camada de Dados**

#### <u>Camada de Domínio</u>
A camada de domínio contém as entidades que representam os dados que o aplicativo manipula. <br>
Cada entidade que vai ser armazenada com o JPA deve ser anotada com `@Entity`. Neste caso, a classe User é uma entidade JPA e será mapeada para uma tabela no banco de dados. A anotação `@Table` define o nome da tabela. A anotação `@Id` indica que o campo id é a chave primária da tabela. A anotação `@GeneratedValue` define que o valor do campo id é gerado automaticamente, através da estratégia `GenerationType.AUTO`.

```java

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
???
1. A classe *UserController* recebe uma instância de *userRepository* através do seu construtor. Como é que este novo repositório é instanciado?
   
    **R:** O Spring Data JPA fornece uma implementação para o repositório. O Spring injeta a implementação no controlador. 

???
2. Listar os métodos invocados no objecto *userRepository* pelo *UserController*. Onde são definidos estes métodos?
   
    **R:** Os métodos invocados são `save()` e `findAll()`. Estes métodos são definidos na interface *CrudRepository*. 
    > Métodos principais
    >
    > `findAll(): List<T>` Permite consultar todos os registos na base de dados
    >
    > `save(T entity): T entity` Permite guardar uma entidade na base de dados
    >
    > `findById(ID id): T entity` Permite aceder a uma entidade através do seu ID
    >
    > `delete(T entity)` Permite eliminar uma entidade

3. Onde é que os dados estão a ser guardados?
   
    **R:** Os dados estão a ser guardados na base de dados H2. Esta é uma base de dados em memória que é criada automaticamente quando a aplicação é executada. Assim, quando  a aplicação é interrompida, os dados são perdidos.
    > Para prevenir este comportamento e assim garantir a persistência dos dados esta dependência deve ser configurada nesse sentido, tal como é descrito na [documentação oficial](http://www.h2database.com/html/features.html#in_memory_databases).

4. Onde é definida a regra para o endereço de correio electrónico *não vazio*?

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

>**Nota:** O ficheiro `application.properties` deve ser colocado no diretório `src/main/resources` do projeto.

## Criar interface REST

> [Tutorial](https://www.javaguides.net/2018/09/spring-boot-2-jpa-mysql-crud-example.html)

> Codigo disponibilizado no [Repositório](https://github.com/RameshMF/spring-boot2-jpa-crud-example)

O objetivo é criar uma API REST para um aplicativo de gerenciamento de funcionários usando Spring Boot 2, Spring Data JPA, MySQL e Maven.

### ** Criar um projeto Maven **

Crie um projeto Maven com o nome `spring-boot2-jpa-crud-example` e adicione as seguintes dependências: 
 1. web;
 2. JPA;
 3. MySQL Driver;
 4. DevTools;
 5. validation.

### ** Criar entidade Employee (Entidades) **
### ** Criar interface EmployeeRepository (Gestão de entidades) **
### ** Criar classe EmployeeController (controlador REST)**
### ** Execeções **
feito 

### ** Dispobilizar a aplicação **
### ** Testar a aplicação **
### ** Testar a aplicação com o Postman **



# Review questions

1. Explain the differences between the RestController and Controller components used in different
parts of this lab. <br>


1. Create a visualization of the Spring Boot layers (UML diagram or similar), displaying the key
abstractions in the solution of 3.3, in particular: entities, repositories, services and REST controllers.
Describe the role of the elements modeled in the diagram. <br>

3. Explain the annotations @Table, @Colum, @Id found in the Employee entity. <br>

4. Explain the use of the annotation @AutoWired (in the Rest Controller class).<br>
   


@utowired para idzer ao spring boot que para e instancia 

