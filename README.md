# Spring PetClinic – Uzorak aplikacije [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## Razumijevanje aplikacije Spring Petclinic kroz nekoliko dijagrama

[Pogledajte prezentaciju ovdje](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Pokretanje Petclinica lokalno

Spring Petclinic je aplikacija [Spring Boot](https://spring.io/guides/gs/spring-boot) izgrađena uz pomoć [Mavena](https://spring.io/guides/gs/maven/) ili [Gradlea](https://spring.io/guides/gs/gradle/). Možete izgraditi JAR datoteku i pokrenuti je iz naredbenog retka (trebalo bi jednako dobro raditi s Javom 17 ili novijom):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(Na Windowsu ili ako vaša ljuska ne proširi glob, možda ćete morati eksplicitno navesti naziv JAR datoteke na kraju naredbe.)

Zatim možete pristupiti Petclinicu na <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

Također ga možete pokrenuti izravno iz Mavena koristeći Spring Boot Maven dodatak. Ako to učinite, pokupit će promjene koje napravite u projektu odmah (promjene u Java izvornoj datoteci također zahtijevaju kompilaciju – većina ljudi za to koristi IDE):

```bash
./mvnw spring-boot:run
```

> NAPOMENA: Ako radije koristite Gradle, aplikaciju možete izgraditi pomoću `./gradlew build` i potražiti JAR datoteku u `build/libs`.

## Izgradnja kontejnerske slike

U projektu ne postoji `Dockerfile`. Možete izgraditi kontejnersku sliku (ako imate docker daemon) koristeći Spring Boot build plugin:

```bash
./mvnw spring-boot:build-image
```

## Prijava greške ili prijedlog poboljšanja za Spring Petclinic

Naš sustav za praćenje problema dostupan je [ovdje](https://github.com/spring-projects/spring-petclinic/issues).

## Konfiguracija baze podataka

U zadanoj konfiguraciji, Petclinic koristi memorijsku bazu (H2) koja se
popuni podacima prilikom pokretanja. H2 konzola je dostupna na `http://localhost:8080/h2-console`,
a sadržaj baze moguće je pregledati korištenjem URL-a `jdbc:h2:mem:<uuid>`. UUID se ispisuje na konzolu pri pokretanju aplikacije.

Slična konfiguracija je osigurana za MySQL i PostgreSQL ako je potrebna trajna baza. Imajte na umu da se, kada se promijeni tip baze, aplikacija mora pokrenuti s drugim profilom: `spring.profiles.active=mysql` za MySQL ili `spring.profiles.active=postgres` za PostgreSQL. Pogledajte [Spring Boot dokumentaciju](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) za više detalja o postavljanju aktivnog profila.

MySQL ili PostgreSQL možete pokrenuti lokalno pomoću instalacijskog programa za vaš OS ili koristeći docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

ili

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

Dodatna dokumentacija dostupna je za [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
i [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

Umjesto "vanilla" `dockera` možete koristiti i priloženu `docker-compose.yml` datoteku za pokretanje kontejnera baza. Svaki ima servis nazvan po Spring profilu:

```bash
docker compose up mysql
```

ili

```bash
docker compose up postgres
```

## Testne aplikacije

Tijekom razvoja preporučujemo korištenje testnih aplikacija postavljenih kao `main()` metode u `PetClinicIntegrationTests` (koristi zadanu H2 bazu i dodaje Spring Boot Devtools), `MySqlTestApplication` i `PostgresIntegrationTests`. One su podešene tako da aplikacije možete pokretati u IDE‑u za brzu povratnu informaciju, a iste se klase mogu pokretati kao integracijski testovi protiv odgovarajućih baza. MySQL integracijski testovi koriste Testcontainers za pokretanje baze u Docker kontejneru, a Postgres testovi koriste Docker Compose za isto.

## Kompiliranje CSS‑a

Datoteka `petclinic.css` nalazi se u `src/main/resources/static/resources/css`. Generirana je iz izvora `petclinic.scss` u kombinaciji s [Bootstrap](https://getbootstrap.com/) bibliotekama. Ako mijenjate `scss` ili nadograđujete Bootstrap, morat ćete ponovno kompilirati CSS resurse koristeći Maven profil "css", tj. `./mvnw package -P css`. Za Gradle ne postoji build profil za kompilaciju CSS‑a.

## Rad s Petclinic projektom u vašem IDE‑u

### Preduvjeti

Sljedeće stavke trebaju biti instalirane na vašem sustavu:

- Java 17 ili novija (puni JDK, ne samo JRE)
- [Git naredbena linija](https://help.github.com/articles/set-up-git)
- Vaš omiljeni IDE
  - Eclipse s m2e dodatkom. Napomena: kada je m2e dostupan, postoji m2 ikona u dijalogu `Help -> About`. Ako m2e nije tamo, slijedite postupak instalacije [ovdje](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Koraci

1. U naredbenoj liniji pokrenite:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. U Eclipseu ili STS‑u:

    Otvorite projekt preko `File -> Import -> Maven -> Existing Maven project`, zatim odaberite korijenski direktorij kloniranog repozitorija.

    Zatim ili izgradite na naredbenoj liniji `./mvnw generate-resources` ili koristite Eclipse pokretač (desni klik na projekt i `Run As -> Maven install`) kako biste generirali CSS. Pokrenite glavnu metodu aplikacije desnim klikom na nju i odabirom `Run As -> Java Application`.

1. U IntelliJ IDEA‑i:

    U glavnom izborniku odaberite `File -> Open` i izaberite Petclinic [pom.xml](pom.xml). Kliknite na gumb `Open`.

    - CSS datoteke generiraju se iz Maven izgradnje. Možete ih generirati na naredbenoj liniji `./mvnw generate-resources` ili desnim klikom na projekt `spring-petclinic` pa `Maven -> Generates sources and Update Folders`.

    - Trebala bi biti kreirana konfiguracija pokretanja naziva `PetClinicApplication` ako koristite noviju Ultimate verziju. U suprotnom, pokrenite aplikaciju desnim klikom na glavnu klasu `PetClinicApplication` i odabirom `Run 'PetClinicApplication'`.

1. Navigacija do Petclinica

    Posjetite [http://localhost:8080](http://localhost:8080) u vašem pregledniku.

## Tražite nešto posebno?

| Spring Boot konfiguracija | Klase ili Java properties datoteke |
|---------------------------|---|
| Glavna klasa | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| Properties datoteke | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| Keširanje | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Zanimljive grane i forkovane verzije Spring Petclinica

"Main" grana Spring Petclinica u GitHub organizaciji [spring-projects](https://github.com/spring-projects/spring-petclinic)
je "kanonska" implementacija temeljena na Spring Bootu i Thymeleafu. Postoji
[prilično mnogo forkova](https://spring-petclinic.github.io/docs/forks.html) u GitHub organizaciji
[spring-petclinic](https://github.com/spring-petclinic). Ako vas zanima drugačiji tehnološki stack za implementaciju Pet Clinica, pridružite se zajednici tamo.

## Interakcija s drugim open‑source projektima

Jedan od najboljih dijelova rada na aplikaciji Spring Petclinic je prilika za izravan rad s mnogim open source projektima. Pronašli smo bugove/prijedloge poboljšanja u različitim područjima poput Springa, Spring Date, Bean Validationa pa čak i Eclipsea! U mnogim slučajevima, popravljeni su/implementirani u samo nekoliko dana.
Evo popisa nekih od njih:

| Naziv | Problem |
|-------|--------|
| Spring JDBC: pojednostaviti korištenje NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) i [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: pojednostaviti Maven ovisnosti i unatrag kompatibilnost | [HV-790](https://hibernate.atlassian.net/browse/HV-790) i [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: omogućiti više fleksibilnosti pri radu s JPQL upitima | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Doprinos

[Issue tracker](https://github.com/spring-projects/spring-petclinic/issues) je preferirani kanal za prijave bugova, zahtjeve za značajke i slanje pull requestova.

Za pull requestove, postavke urednika dostupne su u datoteci [editor config](.editorconfig) radi lakšeg korištenja u uobičajenim uređivačima teksta. Više pročitajte i preuzmite dodatke na <https://editorconfig.org>. Svi commitovi moraju uključivati __Signed-off-by__ trailer na kraju poruke commita kojim doprinositelj potvrđuje suglasnost s Developer Certificate of Origin.
Za dodatne detalje, pogledajte objavu na blogu [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## Licenca

Uzorak aplikacije Spring PetClinic objavljen je pod verzijom 2.0 [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
