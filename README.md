# Aplicación de ejemplo Spring PetClinic (Repositorio junie-intelliJ)

*Nota: Este es un fork de la aplicación original Spring PetClinic, ahora alojada en el repositorio junie-intelliJ.*

[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## Comprender la aplicación Spring Petclinic con algunos diagramas

[Ver la presentación aquí](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Ejecutar Petclinic localmente

Spring Petclinic es una aplicación de [Spring Boot](https://spring.io/guides/gs/spring-boot) construida usando [Maven](https://spring.io/guides/gs/maven/) o [Gradle](https://spring.io/guides/gs/gradle/). Puedes compilar un archivo JAR y ejecutarlo desde la línea de comandos (debería funcionar igual de bien con Java 17 o superior):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(En Windows, o si tu shell no expande el comodín, puede que necesites especificar explícitamente el nombre del archivo JAR al final del comando).

Después podrás acceder a Petclinic en <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

También puedes ejecutarla directamente desde Maven usando el plugin de Spring Boot. Si lo haces, tomará los cambios que realices en el proyecto de forma inmediata (los cambios en archivos fuente Java requieren compilar; la mayoría de la gente usa un IDE para esto):

```bash
./mvnw spring-boot:run
```

> NOTA: Si prefieres usar Gradle, puedes compilar la aplicación con `./gradlew build` y buscar el archivo JAR en `build/libs`.

## Construir una imagen de contenedor

Este proyecto no tiene `Dockerfile`. Puedes construir una imagen de contenedor (si tienes un daemon de Docker) usando el plugin de construcción de Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## En caso de que encuentres un error o una mejora para Spring Petclinic

Nuestro rastreador de issues está disponible [aquí](https://github.com/spring-projects/spring-petclinic/issues).

## Configuración de la base de datos

En su configuración por defecto, Petclinic usa una base de datos en memoria (H2) que
se rellena con datos al inicio. La consola de H2 está expuesta en `http://localhost:8080/h2-console`,
y es posible inspeccionar el contenido de la base de datos usando la URL `jdbc:h2:mem:<uuid>`. El UUID se imprime en la consola al iniciar la aplicación.

Se proporciona una configuración similar para MySQL y PostgreSQL si se necesita una base de datos persistente. Ten en cuenta que siempre que cambie el tipo de base de datos, la aplicación debe ejecutarse con un perfil diferente: `spring.profiles.active=mysql` para MySQL o `spring.profiles.active=postgres` para PostgreSQL. Consulta la [documentación de Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) para más detalles sobre cómo establecer el perfil activo.

Puedes iniciar MySQL o PostgreSQL localmente con el instalador que prefieras para tu SO o usar Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

o

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

Hay documentación adicional para [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
y [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

En lugar de usar `docker` directamente también puedes usar el archivo `docker-compose.yml` proporcionado para iniciar los contenedores de base de datos. Cada uno tiene un servicio con el nombre del perfil de Spring:

```bash
docker compose up mysql
```

o

```bash
docker compose up postgres
```

## Aplicaciones de prueba

Durante el desarrollo te recomendamos usar las aplicaciones de prueba configuradas como métodos `main()` en `PetClinicIntegrationTests` (usando la base de datos H2 por defecto y añadiendo Spring Boot Devtools), `MySqlTestApplication` y `PostgresIntegrationTests`. Estas están configuradas para que puedas ejecutar las apps en tu IDE y obtener retroalimentación rápida y también ejecutar las mismas clases como pruebas de integración contra la base de datos correspondiente. Las pruebas de integración con MySQL usan Testcontainers para iniciar la base de datos en un contenedor Docker, y las de Postgres usan Docker Compose para lo mismo.

## Compilación del CSS

Hay un `petclinic.css` en `src/main/resources/static/resources/css`. Se generó a partir de la fuente `petclinic.scss`, combinada con la librería [Bootstrap](https://getbootstrap.com/). Si haces cambios en el `scss`, o actualizas Bootstrap, necesitarás recompilar los recursos CSS usando el perfil de Maven "css", es decir, `./mvnw package -P css`. No hay un perfil de compilación para Gradle que compile el CSS.

## Trabajar con Petclinic en tu IDE

### Requisitos previos

Debes tener instalados en tu sistema los siguientes elementos:

- Java 17 o superior (JDK completo, no JRE)
- [Herramienta de línea de comandos de Git](https://help.github.com/articles/set-up-git)
- Tu IDE preferido
  - Eclipse con el plugin m2e. Nota: cuando m2e está disponible, hay un icono m2 en el diálogo `Help -> About`. Si m2e no está, sigue el proceso de instalación [aquí](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Pasos

1. En la línea de comandos ejecuta:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Dentro de Eclipse o STS:

    Abre el proyecto mediante `File -> Import -> Maven -> Existing Maven project`, luego selecciona el directorio raíz del repositorio clonado.

    Luego, compila en la línea de comandos con `./mvnw generate-resources` o usa el lanzador de Eclipse (clic derecho en el proyecto y `Run As -> Maven install`) para generar el CSS. Ejecuta el método principal de la aplicación haciendo clic derecho sobre él y eligiendo `Run As -> Java Application`.

1. Dentro de IntelliJ IDEA:

    En el menú principal, elige `File -> Open` y selecciona el [pom.xml](pom.xml) de Petclinic. Haz clic en el botón `Open`.

    - Los archivos CSS se generan desde la compilación de Maven. Puedes generarlos en la línea de comandos con `./mvnw generate-resources` o haciendo clic derecho en el proyecto `spring-petclinic` y luego `Maven -> Generates sources and Update Folders`.

    - Debería haberse creado una configuración de ejecución llamada `PetClinicApplication` si usas una versión Ultimate reciente. De lo contrario, ejecuta la aplicación haciendo clic derecho en la clase principal `PetClinicApplication` y eligiendo `Run 'PetClinicApplication'`.

1. Navega a Petclinic

    Visita [http://localhost:8080](http://localhost:8080) en tu navegador.

## ¿Buscas algo en particular?

|Configuración de Spring Boot | Clases o archivos de propiedades de Java |
|-----------------------------|---|
|Clase principal | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|Archivos de propiedades | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
|Caché | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Ramas y forks interesantes de Spring Petclinic

La rama "main" de Spring Petclinic en la organización de GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic)
es la implementación "canónica" basada en Spring Boot y Thymeleaf. Hay
[bastantes forks](https://spring-petclinic.github.io/docs/forks.html) en la organización de GitHub
[spring-petclinic](https://github.com/spring-petclinic). Si te interesa usar un stack tecnológico diferente para implementar Pet Clinic, únete a la comunidad allí.

## Interacción con otros proyectos de código abierto

Una de las mejores partes de trabajar en la aplicación Spring Petclinic es que tenemos la oportunidad de trabajar en contacto directo con muchos proyectos de código abierto. Encontramos errores y sugerimos mejoras en varios temas como Spring, Spring Data, Bean Validation e incluso Eclipse. En muchos casos, ¡se han corregido/implementado en solo unos días!
Aquí tienes una lista de ellos:

| Nombre | Issue |
|--------|-------|
| Spring JDBC: simplificar el uso de NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) y [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: simplificar dependencias de Maven y compatibilidad hacia atrás | [HV-790](https://hibernate.atlassian.net/browse/HV-790) y [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: proporcionar más flexibilidad al trabajar con consultas JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Contribuir

El [issue tracker](https://github.com/spring-projects/spring-petclinic/issues) es el canal preferido para reportar errores, solicitar funcionalidades y enviar pull requests.

Para pull requests, las preferencias del editor están disponibles en el [editor config](.editorconfig) para usarlas fácilmente en editores de texto comunes. Lee más y descarga plugins en <https://editorconfig.org>. Todos los commits deben incluir un remolque __Signed-off-by__ al final del mensaje del commit para indicar que la persona colaboradora acepta el Developer Certificate of Origin.
Para más detalles, consulta la entrada del blog [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## Licencia

La aplicación de ejemplo Spring PetClinic se publica bajo la versión 2.0 de la [Licencia Apache](https://www.apache.org/licenses/LICENSE-2.0).
