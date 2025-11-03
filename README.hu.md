# Spring PetClinic Mintaalkalmazás [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Megnyitás Gitpodban](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Megnyitás GitHub Codespaces-ben](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## A Spring Petclinic alkalmazás megértése néhány diagramon keresztül

[A bemutató itt érhető el](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Petclinic futtatása helyben

A Spring Petclinic egy [Spring Boot](https://spring.io/guides/gs/spring-boot) alkalmazás, amely [Maven](https://spring.io/guides/gs/maven/) vagy [Gradle](https://spring.io/guides/gs/gradle/) segítségével épül. Készíthetsz JAR fájlt és futtathatod parancssorból (Java 17-tel vagy újabbal működik):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(Windows alatt, vagy ha a shell nem bontja ki a glob mintát, előfordulhat, hogy a JAR fájl nevét pontosan meg kell adnod a parancs végén.)

Ezután a Petclinic alkalmazást a <http://localhost:8080/> címen érheted el.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

Vagy futtathatod közvetlenül Mavenből a Spring Boot Maven bővítménnyel. Ebben az esetben a projektben végzett módosítások azonnal érvényesülnek (a Java forrásfájlok módosításaihoz fordítás is szükséges – a legtöbben erre IDE-t használnak):

```bash
./mvnw spring-boot:run
```

> MEGJEGYZÉS: Ha inkább Gradle-t használsz, az alkalmazást a `./gradlew build` paranccsal építheted, a JAR fájl pedig a `build/libs` mappában lesz.

## Konténer építése

Ebben a projektben nincs `Dockerfile`. Konténerképet a Spring Boot build bővítménnyel tudsz készíteni (ha fut a docker daemon):

```bash
./mvnw spring-boot:build-image
```

## Ha hibát találsz vagy fejlesztési javaslatod van a Spring Petclinic-hez

A hibakövetőnk [itt érhető el](https://github.com/spring-projects/spring-petclinic/issues).

## Adatbázis konfiguráció

Alapértelmezés szerint a Petclinic memóriában futó adatbázist (H2) használ, amely indításkor adatokkal töltődik fel. A H2 konzol a `http://localhost:8080/h2-console` címen érhető el, és az adatbázis tartalma a `jdbc:h2:mem:<uuid>` URL-lel tekinthető meg. Az UUID indításkor jelenik meg a konzolon.

Hasonló beállítás elérhető MySQL-hez és PostgreSQL-hez is, ha tartós adatbázisra van szükség. Fontos, hogy adatbázistípus váltásakor más profillal kell futtatni az alkalmazást: MySQL esetén `spring.profiles.active=mysql`, PostgreSQL esetén `spring.profiles.active=postgres`. További részletek a [Spring Boot dokumentációjában](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) találhatók.

MySQL-t vagy PostgreSQL-t telepíthetsz lokálisan a rendszerednek megfelelő módon, vagy használhatod a dockert:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

vagy

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

További dokumentáció: [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) és [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

A sima `docker` használata helyett a mellékelt `docker-compose.yml` fájlt is használhatod az adatbázis konténerek indításához. Minden szolgáltatás a megfelelő Spring profilról kapta a nevét:

```bash
docker compose up mysql
```

vagy

```bash
docker compose up postgres
```

## Tesztalkalmazások

Fejlesztés közben javasoljuk a `main()` metódussal rendelkező tesztalkalmazások használatát: `PetClinicIntegrationTests` (alapértelmezett H2 adatbázissal és Spring Boot Devtools-szal), `MySqlTestApplication` és `PostgresIntegrationTests`. Ezeket úgy állítottuk be, hogy IDE-ből futtathasd őket a gyors visszajelzés érdekében, valamint ugyanazok az osztályok integrációs tesztekként is futnak a megfelelő adatbázissal. A MySQL integrációs tesztek a Testcontainers könyvtárat használják az adatbázis Docker konténerben történő indításához, a Postgres tesztek pedig Docker Compose-t.

## CSS fordítása

A `src/main/resources/static/resources/css` mappában található egy `petclinic.css` fájl. Ez a `petclinic.scss` forrásból és a [Bootstrap](https://getbootstrap.com/) könyvtárból generálódott. Ha módosítod az `scss`-t, vagy frissíted a Bootstrapet, a CSS erőforrásokat újra kell fordítani a Maven "css" profillal, pl. `./mvnw package -P css`. Gradle-hez nincs külön build profil a CSS fordítására.

## Petclinic használata IDE-ben

### Előfeltételek

A következő elemeknek kell telepítve lenniük a rendszereden:

- Java 17 vagy újabb (teljes JDK, nem csak JRE)
- [Git parancssori eszköz](https://help.github.com/articles/set-up-git)
- Kedvenc IDE-d
  - Eclipse m2e bővítménnyel. Megjegyzés: ha m2e telepítve van, a `Help -> About` ablakban látszik egy m2 ikon. Ha nincs, kövesd a [telepítési útmutatót](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Lépések

1. Parancssorban futtasd:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Eclipse-ben vagy STS-ben:

    Nyisd meg a projektet: `File -> Import -> Maven -> Existing Maven project`, majd válaszd ki a klónozott repo gyökérkönyvtárát.

    Ezután vagy építs parancssorból `./mvnw generate-resources`, vagy használd az Eclipse indítót (jobb klikk a projekten, `Run As -> Maven install`) a CSS generálásához. Az alkalmazás főosztályát jobb klikk -> `Run As -> Java Application`-nel futtasd.

1. IntelliJ IDEA-ban:

    A főmenüben válaszd a `File -> Open` menüpontot és jelöld ki a Petclinic [pom.xml](pom.xml) fájlt. Kattints az `Open` gombra.

    - A CSS fájlok a Maven build során generálódnak. Fordíthatod őket parancssorból `./mvnw generate-resources`, vagy jobb klikk a `spring-petclinic` projekten, majd `Maven -> Generates sources and Update Folders`.

    - Egy `PetClinicApplication` nevű futtatási konfigurációnak automatikusan létre kellett jönnie (újabb Ultimate verzióban). Ha nem, futtasd az alkalmazást a `PetClinicApplication` főosztályon jobb klikkel és a `Run 'PetClinicApplication'` opcióval.

1. Navigáció a Petclinichez

    Látogasd meg a [http://localhost:8080](http://localhost:8080) címet a böngésződben.

## Keresel valamit konkrétan?

| Spring Boot konfiguráció | Osztály vagy Java properties fájlok |
|--------------------------|---|
| Főosztály | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| Properties fájlok | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| Gyorsítótárazás | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Érdekes Spring Petclinic ágak és forkok

A Spring Petclinic „main” ága a [spring-projects](https://github.com/spring-projects/spring-petclinic) GitHub szervezetben a „kanonikus” megvalósítás Spring Boot és Thymeleaf alapon. A szervezetben [számos fork](https://spring-petclinic.github.io/docs/forks.html) található a [spring-petclinic](https://github.com/spring-petclinic) alatt. Ha más technológiai stackkel szeretnéd megvalósítani a Pet Clinic-et, csatlakozz ott a közösséghez.

## Együttműködés más nyílt forrású projektekkel

A Spring Petclinic egyik legjobb része, hogy közvetlen kapcsolatban dolgozhatunk sok nyílt forrású projekttel. Számos témában találtunk hibákat/javasoltunk fejlesztéseket, például a Springben, a Spring Datában, a Bean Validationben és még az Eclipse-ben is! Sok esetben néhány napon belül javították/megvalósították őket. Íme néhány példa:

| Név | Issue |
|------|-------|
| Spring JDBC: a NamedParameterJdbcTemplate használatának egyszerűsítése | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) és [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: Maven függőségek egyszerűsítése és visszafelé kompatibilitás | [HV-790](https://hibernate.atlassian.net/browse/HV-790) és [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: nagyobb rugalmasság JPQL lekérdezéseknél | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Közreműködés

Az [issue tracker](https://github.com/spring-projects/spring-petclinic/issues) a preferált csatorna hibajegyek, feature kérések és pull requestek beküldésére.

A pull requestekhez szerkesztői beállítások érhetők el az [editor config](.editorconfig) fájlban, a legtöbb szövegszerkesztőhöz könnyen használható. További információ és bővítmények: <https://editorconfig.org>. Minden commitnak tartalmaznia kell a __Signed-off-by__ sort az üzenet végén, jelezve, hogy a közreműködő elfogadja a Developer Certificate of Origin-t.
További részletekért lásd a blogbejegyzést: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## Licenc

A Spring PetClinic mintaalkalmazás az [Apache License](https://www.apache.org/licenses/LICENSE-2.0) 2.0-s verziója alatt érhető el.
