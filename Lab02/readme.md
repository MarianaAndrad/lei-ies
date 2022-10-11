# <b> Lab02 - Java at the server-side and the role of application containers  </b>
<br>

author: <b> Mariana Andrade </b>

date: <b> 13/10/2022 </b>

</br>

##  <b>Servlets </b>

Um [**Servlet**](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/Servlet.html) é um programa Java que roda dentro de um servidor web. Servlet é um componente Java que pode ser usado para estender a funcionalidade de um servidor web que suporta o protocolo HTTP. Os servlets podem responder a uma requisição HTTP de um navegador ou outro cliente HTTP. Este pode gerar qualquer conteúdo dinâmico, como um documento HTML, XML ou uma imagem. 
Pode  ainda ser usado para coletar dados do formulário HTML, processá-los e gerar um documento HTML dinâmico como resposta.

Uma vez que se insere no mundo da *web* é fundamental que seja executado num ambiente que permite a concorrência, pelo que deve ser executado num [Servlet Container](https://javaee.github.io/servlet-spec/).

> Quando recebe um pedido do cliente, o servidor entrega o pedido ao Servlet Container, que por sua vez o passa à classe (que implementa o) Servlet. 

<br>

### <b> Apache Tomcat </b>

Para correr, vamos utilizar o servidor de aplicações **Apache Tomcat**.

> 1. Instalar o [Tomcat](https://tomcat.apache.org/)
> 2. Configurar ficheiro tomcat-users.xml [step #6](https://examples.javacodegeeks.com/enterprise-java/servlet/java-servlet-container-example/)
> ``` xml
> <role rolename="manager-gui"/>
> <role rolename="manager-script"/>
> <user username="admin" password="secret" roles="manager-gui, manager-script"/>
> ```
> 3. Executar (corre em http://127.0.0.1:8080/)

<br>
O Tomcat inclui um **gestor do ambiente**, que permite controlar o servidor em http://localhost:8080/manager/. Os utilizadores autorizados são os que têm como role `manager-gui, manager-script` no ficheiro `/etc/tomcat<n>/tomcat-users.xml` configurado no passo 2.

### Criar uma aplicação web

É sugerido utilizar o *archetype* **webapp-javaee7**.

``` bash
$ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -Dversion=1.0-SNAPSHOT -DinteractiveMode=false -DarchetypeArtifactId=webapp-javaee7 -DarchetypeVersion=1.1 -DarchetypeGroupId=org.codehaus.mojo.archetypes
```

> Dentro da pasta **webapp** há várias pastas, uma para cada aplicação.
>
> ```md
> webapp/app1/
> webapp/app2/
> webapp/app3/
> ```
>
> Cada aplicação, por sua vez, pode seguir duas estruturas.
>
> ```
> app/WEB-INF
> app/META-INF
> app/index.jsp
> ```
>
> ```
> app/WEB-INF
> app/META-INF
> app/META-INF/web.xml
> app/META-INF/classes
> ```

<br>

### Compilação

Uma vez criado o projeto, este deve ser testado. Para isso, deve ser compilado e executado.

```bash
$ mvn install
# Este comando deve ser utilizado na primeira compilação e sempre que são adicionadas dependências
$ mvn clean package
# Usar só para compilação
```

Deve ser gerado um ficheiro .war na pasta /target.

> **Web ARchive** (.war) é um arquivo JAR usado para distribuir uma coleção de páginas Servlets.

Este ficheiro é utilizado para fazer *deploy* no Tomcat, em http://localhost:8080/manager/, na secção <u>WAR file to deploy</u>.

<br>

### Ambiente de desenvolvimento integrado com o IDE

[IntelliJ](https://mkyong.com/intellij/intellij-idea-run-debug-web-application-on-tomcat/)

<br>

### Criar uma página web

> Notas retiradas das secçoẽs: **Develop Servlet with @WebServelet Annotation** até **Handling Servelet and Response** [Tutorial](https://howtodoinjava.com/java/servlets/complete-java-servlets-tutorial/#webservlet_annotation)


Para criar páginas web, é necessário criar uma classe que implemente a interface `javax.servlet.Servlet`, que faz *override* do método `service()`, que recebe como parâmetro um objeto `javax.servlet.ServletRequest` e um objeto `javax.servlet.ServletResponse`.

<br>

> Para gerir os pedidos HTTP, é necessário criar uma classe que implemente a interface `javax.servlet.http.HttpServlet`, que extende a interface `javax.servlet.Servlet`. Esta classe deve ser anotada com `@WebServlet` e deve sobrescrever o método `doGet()` ,`doPost()`, `doPut()` e `doDelete()`.

<br>

Para associar a classe a um URL, utiliza-se a anotação [@WebServlet](https://docs.oracle.com/javaee/6/api/javax/servlet/annotation/WebServlet.html).


<br>

```java
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(name = "MyFirstServlet", urlPatterns = {"/MyFirstServlet"})
public class MyFirstServlet extends HttpServlet {
 
    private static final long serialVersionUID = -1915463532411657451L;
 
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException 
    {
        //Do some work
        
    }
}
```
Dentro de cada método pode ser realizado todo o tipo de processamento.<br>

#### Resposta HTML
A resposta pode ser um ficheiro HTML, que é enviado para o cliente, devendo neste caso ser defenido como o tipo de resposta e a pagina ser construida com recurso à classe `PrintWriter`.[!]https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/io/PrintWriter.html

```java
import java.io.PrintWriter;

//...
response.setContentType("text/html");
PrintWriter out = response.getWriter();
try {
    out.println("<html>");
    out.println("<head>");
    out.println("<title>My First Servlet</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>My First Servlet</h1>");
    out.println("</body>");
    out.println("</html>");
} finally {
    out.close();
}
```
<br>

#### Pedido de argumentos
Para obter paramtros dos pedidos, utiliza-se o método `getParameter()` da classe `HttpServletRequest`, que retorne uma `String` com o valor do argumento pedido, ou null caso não exista.

```java
String name = request.getParameter("name");
```

<br>

#### Server-side programming with embedded servers

Em alternativa ao *deploy* num *container*, pode ser utilizado um servidor *embedded* para testar a aplicação,como o [Jetty](https://www.eclipse.org/jetty/documentation/current/embedding-jetty.html). Assim, todo o *life-cycle* da aplicação é gerido pelo servidor, que é executado no próprio programa.

**Jetty server** é um dos servidores mais utilizados para desenvolvimento de aplicações web. É um servidor *embedded* que pode ser utilizado para testar aplicações web sem a necessidade de *deploy* num *container*.

> Utilização [aqui](https://examples.javacodegeeks.com/enterprise-java/jetty/embedded-jetty-server-example/) (3.5.)

<br>
Deve ser adicionada a dependência ao ficheiro `pom.xml`:

```xml
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-server</artifactId>
    <version>9.2.15.v20160210</version>
</dependency>
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-servlet</artifactId>
    <version>9.2.15.v20160210</version>
</dependency>
```
Para correr a aplicação na linha de comandos, deve ainda ser adicionado um *plugin*.

```xml
<plugin>
  <groupId>org.eclipse.jetty</groupId>
  <artifactId>jetty-maven-plugin</artifactId>
  <version>9.3.13.v20161014</version>
</plugin>
```

Para correr a aplicação pelo terminal, deve ser executado o comando `mvn jetty:run`.

<br>

## <b>Spring Boot </b>
String Boot é uma plataforma de desenvolvimento rápido e simples de aplicações *web*, construida sobre o [Spring](https://spring.io/) Framework. É uma ferramenta que permite criar aplicações web com poucas linhas de código, sem a necessidade de configuração de ficheiros XML.

<br>

### Criar projeto maven com Spring 

Gerar o projeto maven com Spring Boot, utilizando o [Spring Initializr](https://start.spring.io/).<br>
Deve ser adicionada a dependência `Spring Web` para criar uma aplicação web. <br>

![](/Lab02/Lab02_3/SpringInitializr.png)

<br>

Para correr a aplicação, deve ser executado os seguintes comandos:

```bash
$ mvn clean package
$ ./mvnw spring-boot:run
```
or 
```bash
$ mvn clean package
$ mvn install -DskipTests && java -jar target\webapp1-0.0.1-SNAPSHOT.jarjava -jar 
```

> Notas:
> O projeto estará disponível em `http://localhost:8080/`, no entanto deverà apresentar um erro, pois ainda não foi criada nenhuma pagina.

![](/Lab02/Lab02_3/Erro1.png)

<br>

### Continuar (alineas b)

# Review questions

1. What are the responsibilities/services of a servlet container?

2. Explain, in brief, the "dynamics" of Model-View-Controller approach used in Spring Boot to serve web content.(You may exemplify with a context of the previous exercises)

3. Inspect the POM.xml for the previous SpringBoot projects. What is the role of the “starters” dependencies?

4. Which annotations are transitively included in the @SpringBootApplication?

5. Search online for the topic “Best practices for REST APIdesign”. From what you could learn, select your “top 5”practices,and briefly explain them in you own words.