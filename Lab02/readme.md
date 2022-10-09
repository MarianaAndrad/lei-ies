# <b> Lab02 - Java at the server-side and the role of application containers  </b>
<br>

author: <b> Mariana Andrade </b>

date: <b> --/10/2022 </b>

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

> [Tutorial](https://howtodoinjava.com/java/servlets/complete-java-servlets-tutorial/#webservlet_annotation)


Para criar páginas web, é necessário criar uma classe que implemente a interface `javax.servlet.Servlet`, que faz *override* do método `service()`, que recebe como parâmetro um objeto `javax.servlet.ServletRequest` e um objeto `javax.servlet.ServletResponse`.

<br>

> Para gerir os pedidos HTTP, é necessário criar uma classe que implemente a interface `javax.servlet.http.HttpServlet`, que extende a interface `javax.servlet.Servlet`. Esta classe deve ser anotada com `@WebServlet` e deve sobrescrever o método `doGet()` ,`doPost()`, `doPut()` e `doDelete()`.

<br>

Para associar a classe a um URL, utiliza-se a anotação [@WebServlet](https://docs.oracle.com/javaee/6/api/javax/servlet/annotation/WebServlet.html).

> exemplo: `@WebServlet("/hello")`
>```java
>@WebServlet("/hello")
>public class HelloServlet extends HttpServlet {
>    @Override
>    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
>        resp.getWriter().write("Hello World!");
>    }
>}
>```

Dentro de cada método pode ser realizado todo o tipo de processamento.

<br>