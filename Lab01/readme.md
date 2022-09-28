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

## <b> GIT </b>
Para entregar ou partilhar um projeto com alguém, é desnecessário enviar o código compilado. Para isso, é necessário utilizar um sistema de controlo de versões, como o [GIT](https://git-scm.com/).

O GIT é um sistema de controlo de versões distribuído, que permite a partilha de código entre várias pessoas, sem a necessidade de um servidor central. Para além disso, permite a criação de branches, que são cópias do projeto, que podem ser utilizadas para testar novas funcionalidades, sem afetar o projeto principal.

Em primeiro lugar, é necessário criar um repositório local, para o qual o projeto será enviado. Para isso, basta executar o seguinte comando no terminal.

```bash
$ git init
```

Para adicionar um ou varios ficheiros ao repositório, basta executar um dos seguintes comandos no terminal.

```bash
$ git add <file> # Adiciona um ficheiro
$ git add . # Adiciona todos os ficheiros
```

Para enviar os ficheiros para o repositório, basta executar o seguinte comando no terminal.

```bash 
$ git commit -m "Mensagem"
```

Para enviar os ficheiros para um repositório remoto, basta executar o seguinte comando no terminal.

```bash
$ git remote add origin <url> # Adiciona um repositório remoto
$ git push -u origin master # Envia os ficheiros para o repositório remoto
```

Para receber os ficheiros de um repositório remoto, basta executar o seguinte comando no terminal.

```bash
$ git pull origin master # Recebe os ficheiros do repositório remoto
```

Para criar um novo branch, basta executar o seguinte comando no terminal.

```bash
$ git branch <branch> # Cria um novo branch
$ git checkout <branch> # Muda para o novo branch
```

Para juntar um branch a outro, basta executar o seguinte comando no terminal.

```bash
$ git checkout <branch> # Muda para o branch que vai receber os ficheiros
$ git merge <branch> # Junta o branch com o branch atual
```

Para apagar um branch, basta executar o seguinte comando no terminal.

```bash
$ git branch -d <branch> # Apaga um branch
```

Nestes links podem encontrar mais informação sobre o GIT.

[Ver mais](https://git-scm.com/docs)

[Ajuda Rápida](https://gist.github.com/leocomelli/2545add34e4fec21ec16)

O git permite que ficheiros sejam ignorados, para que não sejam enviados para o repositório. Para isso, basta criar um ficheiro chamado **.gitignore** e adicionar os nomes dos ficheiros que não querem ser enviados para o repositório.

<u>Nota</u>: 
> Para adicionar código a um repositório a partir de uma pasta já criada, não deve ser feito `git clone`, mas sim iniciado um repositório novo na pasta existente. Para isso, basta ver a documentação acima.

## <b> Docker </b>

> [Documentação](https://docs.docker.com/engine/)

O **Docker** é uma plataforma de virtualização que permite a criação de containers, que são ambientes isolados, que podem ser utilizados para executar aplicações. Os containers são mais leves e rápidos que as VMs, pois não correm um SO completo, mas sim um SO *light*. Para além disso, os containers podem ser facilmente partilhados, o que permite a criação de aplicações escaláveis.

Um **container** é um ambiente isolado, que permite a execução de aplicações, sem a necessidade de instalar o software necessário.

A organização em *containers* permite simplificar o processo de desenvolvimento e execução, oferecendo:

- **Flexibilidade**. Até as aplicações mais complexas podem ser colocadas num *container*;
- **Leveza**. <u>Partilham o núcleo, tornando-los muito mais eficientes do que as máquinas virtuais;</u>
- **Portabilidade**. Uma aplicação pode ser construída localmente, *deployed* na *cloud* e executada por todos;
- **Altamente desacoplado**. São sistemas maioritariamente fechados, podendo ser substituídos sem afetar os restantes.
- **Escalabilidade**. Podem ser aumentados e distribuídos;
- **Segurança**. Por defeito, impõem grandes restrições e isolamentos aos processos. 

As suas funcionalidades destacam-se por fazer *deploy* de aplicações como um objeto (em vez de uma *sandbox*), ser focado na aplicação (e não na máquina), suportar a construção automática de *containers*, ter embutido uma ferramenta de controlo de versões e permitir a reutilização de componentes.

Para além disso, o Docker permite a criação de *images*, que são ficheiros que contêm o sistema operativo e as aplicações necessárias para a execução de um container. Para além disso, as *images* podem ser partilhadas, o que permite a criação de aplicações escaláveis.

Para criar uma *image*, basta criar um ficheiro chamado **Dockerfile** e adicionar as instruções necessárias para a criação da *image*. Para além disso, é possível criar *images* a partir de outras *images*.

### Comandos importantes

Para criar uma imagem, basta executar o seguinte comando no terminal.

```bash
$ docker build -t <image> .
```

Para executar um container, basta executar o seguinte comando no terminal.

```bash
$ docker run -d -p 5000:5000 <image>
```

Para listar os containers, basta executar o seguinte comando no terminal.

```bash
$ docker ps
```

Para listar todas as imagens, basta executar o seguinte comando no terminal.

```bash
$ docker images
```

Para parar um container, basta executar o seguinte comando no terminal.

```bash
$ docker stop <container>
```

Para apagar um container, basta executar o seguinte comando no terminal.

```bash
$ docker rm <container>
```

Para apagar uma imagem, basta executar o seguinte comando no terminal.

```bash
$ docker rmi <image>
```

### Primeiros passsos

[Docker Docs](https://docs.docker.com/get-started/)

```bash
# Get docker version to check if installed correctly
$ docker --version
```

Confirmada a instalação, podem ser descarregadas e executadas imagens.

```bash
# Descarregar e executa a imagem hello-world
$ docker run hello-world
# Listar a imagem descarregada
$ docker image ls
# Listar os containers
$ docker ps --all
```

### Construir e executar uma imagem

[Docker Docs](https://docs.docker.com/get-started/part2/)

O primeiro passo para o desenvolvimento de aplicações *containerized* passa pela <u>criação de *containers* indiduais para cada componente através da criação de uma imagem Docker</u>.


#### Dockerfile
A definição do *container* é feita através do **Dockerfile**, que descreve como o sistema de ficheiros é montado, podendo ainda conter metadados a descrever como correr um *container* com base nesta imagem.

```dockerfile
# Exemplo de um docker file
# Use the official image as a parent image.
FROM node:current-slim

# Set the working directory.
WORKDIR /usr/src/app

# Copy the file from your host to your current location.
COPY package.json .

# Run the command inside your image filesystem.
# Which will read package.json to determine your app’s node dependencies, and install them.
RUN npm install

# Metadata.
# Add metadata to the image to describe which port the container is listening on at runtime.
EXPOSE 8080

# Metadata.
# Says that the containerized process that this image is meant to support is npm start.
# Run the specified command within the container.
CMD [ "npm", "start" ]

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .
```

### Gerir containers em ambiente gráfico

O [*portainer*](https://www.portainer.io/installation/) é uma aplicação *web* que permite aceder através do navegador ao ambiente de gestão do Docker. A sua instalação é bastante simples.

```bash
$ docker volume create portainer_data
$ docker run -d -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce
```

Para lhe aceder basta ir ao endereço [localhost:9000](localhost:9000).

## Review questions


# Referencias
* https://books.sonatype.com/mvnex-book/reference/index.html

* https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

* https://www.tutorialspoint.com/maven/maven_build_life_cycle.htm