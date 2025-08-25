# Spring PetClinic Örnek Uygulaması (junie-intelliJ Deposu)

Not: Bu belge, orijinal Spring PetClinic uygulamasının Türkçe çevirisidir. İçerik, junie-intelliJ deposunda yer alan README.md dosyasına karşılık gelmektedir.

[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Gitpod'da Aç](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![GitHub Codespaces'ta Aç](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## Birkaç diyagram ile Spring Petclinic uygulamasını anlama

[Sunumu burada görün](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Petclinic'i yerelde çalıştırma

Spring Petclinic, [Maven](https://spring.io/guides/gs/maven/) veya [Gradle](https://spring.io/guides/gs/gradle/) kullanılarak oluşturulmuş bir [Spring Boot](https://spring.io/guides/gs/spring-boot) uygulamasıdır. Bir jar dosyası oluşturup komut satırından çalıştırabilirsiniz (Java 17 veya daha yenisiyle sorunsuz çalışır):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(Windows'ta veya kabuğunuz joker karakteri genişletmiyorsa, en sonda JAR dosyasının adını açıkça belirtmeniz gerekebilir.)

Daha sonra Petclinic'e şu adresten erişebilirsiniz: <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

Alternatif olarak, Maven üzerinden doğrudan Spring Boot Maven eklentisini kullanarak çalıştırabilirsiniz. Bunu yaparsanız, projede yaptığınız değişiklikler anında alınır (Java kaynak dosyalarındaki değişiklikler için derleme de gerekir — çoğu kişi bunun için bir IDE kullanır):

```bash
./mvnw spring-boot:run
```

> NOT: Gradle tercih ediyorsanız, uygulamayı `./gradlew build` komutuyla derleyebilir ve jar dosyasını `build/libs` altında bulabilirsiniz.

## Konteyner Oluşturma

Bu projede bir `Dockerfile` yoktur. Bir docker daemon'ınız varsa, Spring Boot build eklentisini kullanarak bir konteyner imajı oluşturabilirsiniz:

```bash
./mvnw spring-boot:build-image
```

## Spring Petclinic için bir hata bulmanız/iyileştirme önermeniz durumunda

Hata takip sistemimize [buradan](https://github.com/spring-projects/spring-petclinic/issues) ulaşabilirsiniz.

## Veritabanı yapılandırması

Varsayılan yapılandırmada Petclinic, başlangıçta verilerle doldurulan bellek içi bir veritabanı (H2) kullanır. H2 konsolu `http://localhost:8080/h2-console` adresinde sunulur ve veritabanı içeriğini `jdbc:h2:mem:<uuid>` URL'sini kullanarak incelemek mümkündür. UUID, başlangıçta konsola yazdırılır.

Kalıcı bir veritabanı yapılandırmasına ihtiyaç duyulursa MySQL ve PostgreSQL için benzer bir kurulum sağlanmıştır. Veritabanı türü değiştiğinde, uygulamanın farklı bir profil ile çalıştırılması gerektiğini unutmayın: MySQL için `spring.profiles.active=mysql`, PostgreSQL için `spring.profiles.active=postgres`. Aktif profilin nasıl ayarlanacağı hakkında daha fazla bilgi için [Spring Boot dokümantasyonuna](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) bakınız.

MySQL veya PostgreSQL'i yerel ortamınızda işletim sisteminize uygun herhangi bir kurulum yöntemiyle başlatabilir veya docker kullanabilirsiniz:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

veya

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

İlave dokümantasyon [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) ve [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt) için sağlanmıştır.

Düz `docker` yerine sağlanan `docker-compose.yml` dosyasını kullanarak da veritabanı konteynerlerini başlatabilirsiniz. Her birinin Spring profiline göre adlandırılmış bir servisi vardır:

```bash
docker compose up mysql
```

veya

```bash
docker compose up postgres
```

## Test Uygulamaları

Geliştirme zamanında `PetClinicIntegrationTests` içindeki `main()` metodu (varsayılan H2 veritabanı ve ek olarak Spring Boot Devtools ile), `MySqlTestApplication` ve `PostgresIntegrationTests` olarak ayarlanan test uygulamalarını kullanmanızı öneririz. Bunlar, IDE'nizde uygulamaları hızlı geri bildirim için çalıştırmanıza ve aynı sınıfları ilgili veritabanına karşı entegrasyon testleri olarak çalıştırmanıza olanak sağlayacak şekilde yapılandırılmıştır. MySQL entegrasyon testleri, veritabanını bir Docker konteynerinde başlatmak için Testcontainers kullanır; Postgres testleri ise aynı şeyi yapmak için Docker Compose kullanır.

## CSS'i Derlemek

`src/main/resources/static/resources/css` altında bir `petclinic.css` bulunmaktadır. Bu dosya, `petclinic.scss` kaynağından ve [Bootstrap](https://getbootstrap.com/) kütüphanesi ile birleştirilerek oluşturulmuştur. `scss` üzerinde değişiklik yapar veya Bootstrap'i yükseltirseniz, CSS kaynaklarını Maven "css" profili ile yeniden derlemeniz gerekir, örn. `./mvnw package -P css`. CSS'i derlemek için Gradle tarafında bir profil bulunmamaktadır.

## Petclinic ile IDE'nizde çalışma

### Önkoşullar

Sisteminizde aşağıdaki öğeler kurulu olmalıdır:

- Java 17 veya daha yeni (tam JDK, yalnızca JRE değil)
- [Git komut satırı aracı](https://help.github.com/articles/set-up-git)
- Tercih ettiğiniz IDE
  - m2e eklentisi ile Eclipse. Not: m2e yüklüyse, `Help -> About` penceresinde m2 simgesi görünür. m2e yoksa, [buradaki](https://www.eclipse.org/m2e/) kurulum adımlarını takip edin.
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Adımlar

1. Komut satırında şunu çalıştırın:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Eclipse veya STS içinde:

    Projeyi `File -> Import -> Maven -> Existing Maven project` yoluyla açın, ardından klonladığınız deponun kök dizinini seçin.

    Daha sonra CSS'i üretmek için ya komut satırından `./mvnw generate-resources` çalıştırın ya da Eclipse başlatıcısını kullanın (projeye sağ tıklayın ve `Run As -> Maven install`) . Uygulamanın ana metodunu, üzerine sağ tıklayıp `Run As -> Java Application` seçerek çalıştırın.

1. IntelliJ IDEA içinde:

    Ana menüden `File -> Open`'ı seçin ve Petclinic'in [pom.xml](pom.xml) dosyasını açın. `Open` düğmesine tıklayın.

    - CSS dosyaları Maven derlemesinden üretilir. Bunları komut satırından `./mvnw generate-resources` ile derleyebilir veya `spring-petclinic` projesine sağ tıklayıp `Maven -> Generates sources and Update Folders` seçeneğini kullanabilirsiniz.

    - Son sürüm Ultimate kullanıyorsanız sizin için `PetClinicApplication` adlı bir çalışma yapılandırması oluşturulmuş olmalıdır. Aksi halde, `PetClinicApplication` ana sınıfına sağ tıklayıp `Run 'PetClinicApplication'` seçeneğini seçerek uygulamayı çalıştırın.

1. Petclinic'e gidin

    Tarayıcınızda [http://localhost:8080](http://localhost:8080) adresini ziyaret edin.

## Özellikle bir şey mi arıyorsunuz?

| Spring Boot Yapılandırması | Sınıf veya Java özellik dosyaları |
|----------------------------|-----------------------------------|
| Ana Sınıf | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| Özellik Dosyaları | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| Önbellekleme | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## İlginç Spring Petclinic dalları ve çatalları

[spring-projects](https://github.com/spring-projects/spring-petclinic) GitHub organizasyonundaki Spring Petclinic "main" dalı, Spring Boot ve Thymeleaf tabanlı "kanonik" uygulamadır. GitHub organizasyonu [spring-petclinic](https://github.com/spring-petclinic) içinde [hayli fazla çatal](https://spring-petclinic.github.io/docs/forks.html) bulunmaktadır. Pet Clinic'i farklı bir teknoloji yığınıyla uygulamakla ilgileniyorsanız, lütfen oradaki topluluğa katılın.

## Diğer açık kaynak projeleriyle etkileşim

Spring Petclinic uygulaması üzerinde çalışmanın en güzel yanlarından biri, pek çok Açık Kaynak projesiyle doğrudan temas halinde çalışma fırsatına sahip olmamızdır. Spring, Spring Data, Bean Validation ve hatta Eclipse gibi çeşitli konularda hata bulduk/iyileştirmeler önerdik! Çoğu durumda, bunlar birkaç gün içinde düzeltildi/uygulandı.
İşte bunların bir listesi:

| Ad | Konu |
|----|------|
| Spring JDBC: NamedParameterJdbcTemplate kullanımının basitleştirilmesi | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) ve [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: Maven bağımlılıklarının basitleştirilmesi ve geriye dönük uyumluluk | [HV-790](https://hibernate.atlassian.net/browse/HV-790) ve [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: JPQL sorguları ile çalışırken daha fazla esneklik sağlanması | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Katkıda bulunma

Hata raporları, özellik istekleri ve pull request göndermek için tercih edilen kanal [issue tracker](https://github.com/spring-projects/spring-petclinic/issues)'dır.

Pull request'ler için, yaygın metin editörlerinde kolay kullanım adına [editor config](.editorconfig) içinde düzenleyici tercihleri mevcuttur. Daha fazlasını okuyun ve eklentileri <https://editorconfig.org> adresinden indirin. Tüm commit'ler, katkıda bulunanın Geliştirici Menşe Belgesi'ni (Developer Certificate of Origin) kabul ettiğini belirtmek için commit mesajının sonunda __Signed-off-by__ satırı içermelidir.
Ek ayrıntılar için şu blog yazısına bakınız: [Merhaba DCO, Güle Güle CLA: Spring'e Katkıları Basitleştirme](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## Lisans

Spring PetClinic örnek uygulaması, [Apache Lisansı](https://www.apache.org/licenses/LICENSE-2.0) sürüm 2.0 altında yayımlanmaktadır.
