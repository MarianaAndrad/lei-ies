# <b> Lab01 - Team practices for enterprise Java development </b>

author: <b> Mariana Andrade </b>

date: <b> 2020-09-01 </b>

## Maven

O Maven é um  <b>project management tool</b> que reúne um objeto de projeto modelo, um conjunto de padrões, um ciclo de vida do projeto, uma dependência sistema de gerenciamento e lógica para executar metas de plug-in em fases de um ciclo de vida.


Trata-se de uma <b>building tool</b> Java, que coordena as várias [etapas](https://raw.githubusercontent.com/dtonhofer/diagrams/master/Maven_Lifecycle/Maven_Lifecycle.png) de construção de um projeto, tais como a obtenção de dependências, compilação do código fonte, testes, produção de binários, atualização da documentação ou mesmo instalação no servidor.

Este pode ser manipulado através da linha de comandos ou de qualquer IDE.

### Primeiros passos
#### <b> Confirmar instalação </b>
 1. Verificar a instalação do Java: `javac -version` 
       * Caso utilize uma versão mais recente, instalar o Java OpenJDK 17: `sudo apt install openjdk-17-jdk`
  
 2. Verificar a instalação do Maven: `mvn -version`
   * expetado: 
        > Apache Maven 3.8.6
        > 
        > Maven home: /home/mariana/apache-maven-3.8.6
        > 
        > Java version: 17.0.4, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
        >
        >Default locale: pt_PT, platform encoding: UTF-8 

### <b> Criar um projeto </b>
Para [criar um projeto](https://books.sonatype.com/mvnex-book/reference/simple-project-sect-create-simple.html), executa-se o comando `mvn`, seguido do *plugin* `archetype` e do objetivo `generate`.



```bash
    $ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -Dversion=1.0-SNAPSHOT -DinteractiveMode=false -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
```
>**Archetype** pode traduzir-se por protótipo, sendo que são disponibilizados vários dos mais simples até aos mais complexos. Por default é instalado o `org.apache.maven.archetypes:maven-archetype-quickstart`, pelo que em alguns casos este comando pode ser ignorado.
>
>**groupId** é um identificador global do projeto. Deve começar com o inverso do nome do domínio controlado.(utilizado no projeto pedido : pt.ua.deti) ([Regras](https://docs.oracle.com/javase/specs/jls/se6/html/packages.html#7.7))
>
>**artifactId** é o nome do jar sem a versão associada, tratando também de um ID do projeto. (por exemplo lab1)
>
>**version** é a versão do projeto. A versão inicial deve ser 1.0-SNAPSHOT

#### <b> Project structure </b>


O [diretório](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) do projeto começa por ter um ficheiro **pom.xml**, onde o projeto é descrito.

Tem também duas pastas principais: a **src** que contém todo o código fonte do projeto e a **target**, onde é armazenada a saída da construção do mesmo.

<br>

![alt text](https://media.discordapp.net/attachments/887155995887960085/1024710462690037841/Captura_de_ecra_de_2022-09-28_16-50-18.png)


#### <b> Compilar o projeto </b>

O protótipo utilizado para criar o projeto terá duas classes criadas: uma na pasta **src/main** e outra na **srt/test**. Para testarmos que o projeto foi construído sem problemas, podem executá-lo através do terminal.

```bash
$ mvn package 														# Compilar
$ mvn exec:java - Dexec.mainClass="pt.ua.deti.WeatherStarter" #adapt para corresponder ao seu próprio pacote e nome de class
$ mvn package exec:java -Dexec.mainClass="pt.ua.deti.WeatherStarter" # Compila e executa
$ mvn package exec:java -Dexec.mainClass=pt.ua.deti.WeatherStarter -Dexec.args="1010500" # Compila e executa com argumentos

```

## <b> POM </b>
Na Imagem antes vista, podemos ver que o ficheiro **pom.xml** contém a informação do projeto, incluindo as dependências necessárias para a sua construção. Trata-se um arquivo XML,que para além dos dados escritos por defeito na construção do projeto, podem ser adicionados detalhes adicionais, como licenças ou a constituição da equipa. [Mais info.](https://books.sonatype.com/mvnex-book/reference/customizing-sect-customizing-project-info.html)

### <b>Dependências </b>
As dependências são geridas através do [Maven Central Repository](https://mvnrepository.com/), que contém uma lista de artefactos que podem ser utilizados no projeto. Para adicionar uma dependência, basta adicionar o seguinte código ao ficheiro **pom.xml**.

```xml
<project>
	<dependencies>
    	<dependency>
        	<groupId></groupId>
            <artifactId></artifactId>
            <version></version>
        </dependency>
    </dependencies>
</project>
```

### <b>Plugins</b>
Os plugins são utilizados para adicionar funcionalidades ao projeto, como por exemplo a criação de um ficheiro jar. Para adicionar um plugin, basta adicionar o seguinte código ao ficheiro **pom.xml**.

```xml
<project>
    <build>
    	<plugins>
        	<plugin>
            	<groupId></groupId>
                <artifactId></artifactId>
                <version></version>
            </plugin>
        </plugins>
    </build>
</project>
```

## Maven life-cycle
Um Build Lifecycle é uma sequência bem definida de fases, que definem a ordem na qual os objetivos devem ser executados. Aqui fase representa um estágio no ciclo de vida.

Quando o Maven começa a construir um projeto, ele percorre uma sequência definida de fases e executa metas, que são registradas em cada fase.

O Maven tem os três ciclos de vida padrão a seguir:

 1. clean
 2. default/build
 3. site

**Clean** - O ciclo de vida clean é responsável por limpar os artefatos criados pelo ciclo de vida build. O ciclo de vida clean é executado antes do ciclo de vida build.

**Default/Build** - O ciclo de vida padrão é responsável por construir o projeto. O ciclo de vida padrão é executado quando nenhum ciclo de vida é especificado.

**Site** - O ciclo de vida site é responsável por gerar o site do projeto. O ciclo de vida site é executado quando nenhum ciclo de vida é especificado.

## GIT

## <b> Docker </b>

## Review questions


# Referencias
* https://books.sonatype.com/mvnex-book/reference/index.html

* https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

* https://www.tutorialspoint.com/maven/maven_build_life_cycle.htm