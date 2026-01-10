# Spring PetClinic mintaalkalmazás [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Megnyitás Gitpodban](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Megnyitás GitHub Codespaces-ben](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## A Spring Petclinic alkalmazás megértése néhány ábrával

[Az előadás megtekintése itt](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Petclinic futtatása helyben

A Spring Petclinic egy [Spring Boot](https://spring.io/guides/gs/spring-boot) alkalmazás, amely [Maven](https://spring.io/guides/gs/maven/) vagy [Gradle](https://spring.io/guides/gs/gradle/) segítségével építhető. Készíthető belőle JAR fájl és futtatható parancssorból (Java 17-tel vagy újabbal egyaránt működik):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(Windows alatt, vagy ha a shell nem terjeszti ki a csillagot, a JAR fájl nevét a parancs végén explicit meg kell adni.)

Ezt követően a Petclinic az alábbi címen érhető el: <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

Vagy futtathatja közvetlenül Mavenből a Spring Boot Maven pluginnal. Így a projektben végzett módosítások azonnal érvényesülnek (a Java források módosítása esetén fordítás is szükséges – a legtöbben ehhez IDE-t használnak):

```bash
./mvnw spring-boot:run
```

> MEGJEGYZÉS: Ha inkább Gradle-t használna, az alkalmazás a `./gradlew build` paranccsal építhető, a JAR fájl pedig a `build/libs` könyvtárban található.

## Konténer építése

Ebben a projektben nincs `Dockerfile`. Konténer image-et (ha fut egy docker démon) a Spring Boot build plugin segítségével építhet:

```bash
./mvnw spring-boot:build-image
```

## Ha hibát talál / fejlesztési javaslata van a Spring Petclinic kapcsán

A hibakövetőnk itt érhető el: [link](https://github.com/spring-projects/spring-petclinic/issues).

## Adatbázis konfiguráció

Alapértelmezett beállítások mellett a Petclinic egy memóriában futó (H2) adatbázist használ, amely indításkor adatokkal töltődik fel. A H2 konzol a `http://localhost:8080/h2-console` címen érhető el, és az adatbázis tartalma a `jdbc:h2:mem:<uuid>` URL-lel tekinthető meg. A UUID indításkor a konzolra íródik ki.

MySQL és PostgreSQL számára hasonló beállítás érhető el, ha tartós adatbázis szükséges. Fontos, hogy adatbázistípus váltásakor más profillal kell futtatni az alkalmazást: MySQL esetén `spring.profiles.active=mysql`, PostgreSQL esetén `spring.profiles.active=postgres`. További részletek a [Spring Boot dokumentációjában](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles).

A MySQL vagy PostgreSQL helyben indítható az OS-nek megfelelő telepítővel, vagy dockerrel:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

vagy

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

További dokumentáció: [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) és [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

A sima `docker` helyett használhatja a mellékelt `docker-compose.yml` fájlt is az adatbázis konténerek indításához. Mindegyik szolgáltatás neve megegyezik a Spring profilléval:

```bash
docker compose up mysql
```

vagy

```bash
docker compose up postgres
```

## Tesztalkalmazások

Fejlesztés közben javasoljuk a `PetClinicIntegrationTests` (alapértelmezett H2 adatbázissal és Spring Boot Devtools-szal), a `MySqlTestApplication` és a `PostgresIntegrationTests` osztályban található `main()` metódusok használatát. Ezek úgy vannak beállítva, hogy az IDE-ből futtathatók legyenek a gyors visszajelzésért, és ugyanazok az osztályok integrációs tesztekként is futnak a megfelelő adatbázis ellen. A MySQL integrációs tesztek a Testcontainers-t használják a DB indításához Docker konténerben, a Postgres tesztek pedig Docker Compose-t.

## CSS fordítása

A `petclinic.css` a `src/main/resources/static/resources/css` könyvtárban található. A `petclinic.scss` forrásból, a [Bootstrap](https://getbootstrap.com/) könyvtárral kombinálva generáltuk. Ha módosítja az `scss`-t, vagy frissíti a Bootstrapet, a CSS erőforrásokat újra kell fordítani a Maven "css" profillal, azaz `./mvnw package -P css`. Gradle-hez nincs build profil a CSS fordításához.

## Petclinic használata IDE-ben

### Előkövetelmények

A rendszerén a következők legyenek telepítve:

- Java 17 vagy újabb (teljes JDK, nem csak JRE)
- [Git parancssori eszköz](https://help.github.com/articles/set-up-git)
- Kedvenc IDE-je
  - Eclipse m2e bővítménnyel. Megjegyzés: ha az m2e elérhető, a `Help -> About` ablakban egy m2 ikon látható. Ha nincs ott, a telepítési útmutató [itt](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Lépések

1. Parancssorban futtassa:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Eclipse-ben vagy STS-ben:

    Nyissa meg a projektet a `File -> Import -> Maven -> Existing Maven project` menüponttal, majd válassza ki a klónozott repo gyökerét.

    Ezután vagy építsen parancssorból `./mvnw generate-resources`, vagy használja az Eclipse indítót (jobb klikk a projekten, `Run As -> Maven install`) a CSS generálásához. Az alkalmazás fő metódusát jobb klikk -> `Run As -> Java Application` menüponttal futtassa.

1. IntelliJ IDEA-ban:

    A főmenüben válassza a `File -> Open` menüpontot és jelölje ki a Petclinic [pom.xml](pom.xml) fájlt. Kattintson az `Open` gombra.

    - A CSS fájlokat a Maven build generálja. Fordíthatja parancssorból `./mvnw generate-resources`, vagy jobb klikk a `spring-petclinic` projekten, majd `Maven -> Generates sources and Update Folders`.

    - Egy `PetClinicApplication` nevű futtatási konfigurációt a frissebb Ultimate verziók automatikusan létrehoznak. Ennek hiányában futtassa az alkalmazást a `PetClinicApplication` főosztály jobb klikk -> `Run 'PetClinicApplication'` menüponttal.

1. Navigálás a Petclinichez

    Látogassa meg a [http://localhost:8080](http://localhost:8080) címet a böngészőjében.

## Keres valamit konkrétan?

| Spring Boot konfiguráció | Osztály vagy property fájlok |
|--------------------------|---|
| Főosztály | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| Properties fájlok | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| Gyorsítótárazás | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Érdekes Spring Petclinic branchek és forkok

A [spring-projects](https://github.com/spring-projects/spring-petclinic) GitHub szervezetben található Spring Petclinic "main" ág a kanonikus megvalósítás Spring Boot és Thymeleaf alapokon. Számos [fork](https://spring-petclinic.github.io/docs/forks.html) elérhető a [spring-petclinic](https://github.com/spring-petclinic) szervezetben. Ha más technológiai stackkel szeretné megvalósítani a Pet Clinicet, csatlakozzon a közösséghez.

## Együttműködés más nyílt forrású projektekkel

A Spring Petclinic alkalmazáson dolgozva közvetlen kapcsolatban állunk számos nyílt forráskódú projekttel. Hibákat találtunk / fejlesztési javaslatokat tettünk több területen, például Spring, Spring Data, Bean Validation és még Eclipse! Sok esetben napokon belül javításra/megvalósításra kerültek. Íme néhány példa:

| Név | Issue |
|------|-------|
| Spring JDBC: a NamedParameterJdbcTemplate használatának egyszerűsítése | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) és [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: Maven függőségek egyszerűsítése és visszafelé kompatibilitás | [HV-790](https://hibernate.atlassian.net/browse/HV-790) és [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: nagyobb rugalmasság JPQL lekérdezéseknél | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Közreműködés

A [hibakövető](https://github.com/spring-projects/spring-petclinic/issues) az ajánlott csatorna hibajegyek, feature kérések és pull requestek beküldésére.

A pull requestekhez az szerkesztőbeállítások az [editor config](.editorconfig) fájlban érhetők el, így könnyen használhatók a legtöbb szövegszerkesztőben. További információk és bővítmények: <https://editorconfig.org>. Minden commitnak tartalmaznia kell egy __Signed-off-by__ trailert az üzenet végén, jelezve, hogy a közreműködő elfogadja a Developer Certificate of Origin-t. További részletek: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## Licenc

A Spring PetClinic mintaalkalmazás az [Apache License](https://www.apache.org/licenses/LICENSE-2.0) 2.0-s verziója alatt érhető el.
