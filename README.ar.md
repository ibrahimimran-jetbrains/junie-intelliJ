# تطبيق Spring PetClinic النموذجي (مستودع junie-intelliJ)

*ملاحظة: هذه تفريعة (Fork) من تطبيق Spring PetClinic الأصلي، وهي مستضافة الآن في مستودع junie-intelliJ.*

[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![افتح في Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![افتح في GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring Petclinic عبر بعض المخططات

[اطّلع على العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

يعد Spring Petclinic تطبيقًا مبنيًا باستخدام [Spring Boot](https://spring.io/guides/gs/spring-boot)، ويمكن بناؤه باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك إنشاء ملف JAR وتشغيله من سطر الأوامر (يعمل مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على Windows، أو إذا لم يقم الـ shell لديك بتوسيع النمط النجمي، قد تحتاج إلى تحديد اسم ملف الـ JAR صراحةً في نهاية الأمر.)

بعد ذلك، يمكنك الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

كما يمكنك تشغيله مباشرةً من Maven باستخدام إضافة Spring Boot الخاصة بـ Maven. إذا فعلت ذلك، فسوف يلتقط أي تغييرات تُجريها على المشروع فورًا (التغييرات على ملفات Java تتطلب أيضًا عملية ترجمة/Compile — معظم المطورين يستخدمون IDE لهذا الغرض):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضل استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` ثم البحث عن ملف الـ JAR داخل `build/libs`.

## بناء حاوية (Container)

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك مع ذلك بناء صورة للحاوية (إذا كان لديك خادوم Docker daemon) باستخدام إضافة البناء الخاصة بـ Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدت خطأ/لديك اقتراح لتحسين Spring Petclinic

متتبع القضايا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## تهيئة قاعدة البيانات

في الإعدادات الافتراضية، يستخدم Petclinic قاعدة بيانات داخل الذاكرة (H2) يتم تعبئتها بالبيانات عند الإقلاع. واجهة h2 متاحة على `http://localhost:8080/h2-console`، ومن الممكن فحص محتوى قاعدة البيانات باستخدام الرابط `jdbc:h2:mem:<uuid>`. يتم طباعة قيمة الـ UUID عند الإقلاع في وحدة التحكم (Console).

كما تتوفر تهيئة مشابهة لكلٍ من MySQL وPostgreSQL إذا كانت هناك حاجة إلى قاعدة بيانات دائمة. لاحظ أنه في كل مرة يتغير فيها نوع قاعدة البيانات، يحتاج التطبيق إلى التشغيل مع ملف تعريف (Profile) مختلف: استخدم `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [توثيق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية ضبط الملف التعريفي النشط.

يمكنك تشغيل MySQL أو PostgreSQL محليًا باستخدام أي مُثبّت يناسب نظام التشغيل لديك، أو باستخدام Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

تتوفر وثائق إضافية لكلٍ من [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلاً من استخدام `docker` مباشرةً يمكنك أيضًا استخدام ملف `docker-compose.yml` المرفق لتشغيل حاويات قواعد البيانات. لكل قاعدة بيانات خدمة تحمل اسم ملف التعريف الخاص بـ Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات للاختبار

نوصي أثناء التطوير باستخدام التطبيقات الاختبارية المُعدة كدوال `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية وإضافة Spring Boot Devtools)، و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه التطبيقات بحيث يمكنك تشغيلها داخل بيئة التطوير IDE للحصول على تغذية راجعة سريعة، وكذلك تشغيل نفس الأصناف كاختبارات تكامل ضد قاعدة البيانات المقابلة. تستخدم اختبارات تكامل MySQL حزمة Testcontainers لتشغيل قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres Docker Compose للغرض نفسه.

## ترجمة ملفات CSS

يوجد ملف `petclinic.css` داخل `src/main/resources/static/resources/css`. تم توليده من المصدر `petclinic.scss` مع دمجه مع مكتبة [Bootstrap](https://getbootstrap.com/). إذا قمت بإجراء تغييرات على ملفات `scss`، أو قمت بترقية Bootstrap، فستحتاج إلى إعادة ترجمة موارد CSS باستخدام ملف تعريف Maven المسمى "css"، أي عبر الأمر: `./mvnw package -P css`. لا يتوفر ملف تعريف مماثل في Gradle لترجمة CSS.

## العمل على Petclinic في بيئة التطوير (IDE)

### المتطلبات المسبقة

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (حزمة JDK كاملة، وليس JRE)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- بيئة التطوير المفضلة لديك
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متاحة، ستجد أيقونة m2 في نافذة `Help -> About`. إن لم تكن موجودة، فاتبع عملية التثبيت من [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project`، ثم اختر الدليل الجذر للمستودع المستنسخ.

    بعد ذلك إمّا أن تبني من سطر الأوامر `./mvnw generate-resources` أو تستخدم مشغّل Eclipse (انقر يمينًا على المشروع ثم `Run As -> Maven install`) لتوليد ملفات CSS. شغّل الدالة الرئيسية للتطبيق عبر النقر يمينًا على الصنف الرئيسي واختيار `Run As -> Java Application`.

1. داخل IntelliJ IDEA:

    من القائمة الرئيسية اختر `File -> Open` وحدد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. ثم انقر زر `Open`.

    - يتم توليد ملفات CSS من خلال عملية البناء في Maven. يمكنك توليدها عبر سطر الأوامر `./mvnw generate-resources` أو بالنقر الأيمن على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - ينبغي أن يتم إنشاء تهيئة تشغيل باسم `PetClinicApplication` تلقائيًا إذا كنت تستخدم إصدار Ultimate حديث. إن لم يكن كذلك، شغّل التطبيق عبر النقر يمينًا على الصنف الرئيسي `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

1. تصفّح تطبيق Petclinic

    تفضّل بزيارة [http://localhost:8080](http://localhost:8080) في متصفحك.

## هل تبحث عن شيء محدّد؟

| إعدادات Spring Boot | ملفات الأصناف أو خصائص Java |
|---------------------|-----------------------------|
| الصنف الرئيسي (Main Class) | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت (Caching) | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع ونسخ (Forks) مثيرة للاهتمام لتطبيق Spring Petclinic

إن فرع "main" من Spring Petclinic في منظمة GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic) هو التطبيق "المرجعي" المبني على Spring Boot وThymeleaf. توجد [عدد من النسخ (forks)](https://spring-petclinic.github.io/docs/forks.html) ضمن منظمة GitHub
[spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام حزمة تقنية مختلفة لتنفيذ Pet Clinic، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع مفتوحة المصدر أخرى

من أفضل الأمور في العمل على تطبيق Spring Petclinic أننا نمتلك فرصة للتواصل المباشر مع العديد من مشاريع المصدر المفتوح. لقد وجدنا أخطاءً/أوصينا بتحسينات في مواضيع متعددة مثل Spring وSpring Data وBean Validation وحتى Eclipse! وفي كثير من الحالات، تم إصلاحها/تنفيذها خلال أيام قليلة.
فيما يلي قائمة ببعضها:

| الاسم | القضية |
|------|--------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven وضمان التوافق العكسي | [HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مرونة أكبر عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

يُعدّ [متتبع القضايا](https://github.com/spring-projects/spring-petclinic/issues) القناة المفضلة للإبلاغ عن الأخطاء وطلب الميزات الجديدة وتقديم طلبات السحب (Pull Requests).

بالنسبة لطلبات السحب، تتوفر تفضيلات المحرر في ملف [editor config](.editorconfig) لتسهيل استخدامها في أشهر المحررات. اقرأ المزيد وحمّل الإضافات من <https://editorconfig.org>. يجب أن تتضمن جميع الالتزامات (Commits) سطر __Signed-off-by__ في نهاية رسالة الالتزام للدلالة على موافقة المساهم على شهادة المطوّر (Developer Certificate of Origin). للمزيد من التفاصيل، يرجى الرجوع إلى التدوينة: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

يتم إصدار تطبيق Spring PetClinic النموذجي بموجب الإصدار 2.0 من [رخصة أباتشي](https://www.apache.org/licenses/LICENSE-2.0).
