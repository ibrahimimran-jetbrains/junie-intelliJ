# تطبيق Spring PetClinic النموذجي (مستودع junie-intelliJ)

[English](README.md) | العربية

*ملاحظة: هذا تفرّع (fork) من تطبيق Spring PetClinic الأصلي، وهو الآن مستضاف في مستودع junie-intelliJ.*

[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring Petclinic عبر بعض المخططات

[شاهد العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

تطبيق Spring Petclinic هو تطبيق [Spring Boot](https://spring.io/guides/gs/spring-boot) مبني باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك بناء ملف jar وتشغيله من سطر الأوامر (يجب أن يعمل بشكل جيد مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على Windows، أو إذا كان الغلاف shell لديك لا يوسّع نمط النجمة، قد تحتاج لتحديد اسم ملف JAR صراحة في نهاية الأمر.)

يمكنك بعدها الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

أو يمكنك تشغيله مباشرة من Maven باستخدام إضافة Spring Boot Maven. إذا فعلت ذلك فسيتم التقاط التغييرات التي تجريها على المشروع فورًا (التغييرات على ملفات Java تتطلب أيضًا عملية تجميع compile — غالبًا ما يستخدم معظم الناس بيئة تطوير IDE لهذا الغرض):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضّل استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` ثم ابحث عن ملف jar في `build/libs`.

## بناء حاوية Container

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك خادم Docker) باستخدام إضافة بناء Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدت خطأ/لديك اقتراح تحسين لـ Spring Petclinic

متعقّب القضايا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## إعداد قاعدة البيانات

في الضبط الافتراضي، يستخدم Petclinic قاعدة بيانات داخل الذاكرة (H2) والتي يتم تعبئتها بالبيانات عند بدء التشغيل. وحدة تحكّم h2 متاحة على `http://localhost:8080/h2-console`، ومن الممكن تفقد محتوى قاعدة البيانات باستخدام الرابط `jdbc:h2:mem:<uuid>`. يتم طباعة قيمة UUID عند بدء التشغيل في وحدة التحكم.

يوفَّر إعداد مشابه لكل من MySQL وPostgreSQL إذا كانت هناك حاجة لقاعدة بيانات دائمة. لاحظ أنه كلما تغيّر نوع قاعدة البيانات، يجب تشغيل التطبيق بملف تعريف (profile) مختلف: استخدم `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [توثيق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية ضبط الملف النشط.

يمكنك تشغيل MySQL أو PostgreSQL محليًا باستخدام أي مُثبّت يناسب نظام التشغيل لديك أو استخدام Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

توجد توثيقات إضافية لكل من [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلًا من استخدام `docker` مباشرة يمكنك أيضًا استخدام ملف `docker-compose.yml` المرفق لبدء حاويات قواعد البيانات. كل واحدة لها خدمة تحمل اسم ملف تعريف Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

أثناء التطوير نوصي باستخدام تطبيقات الاختبار المُعدة كأساليب `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية بالإضافة إلى Spring Boot Devtools)، و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه التطبيقات بحيث يمكنك تشغيلها في بيئة التطوير IDE للحصول على تغذية راجعة سريعة، وأيضًا تشغيل نفس الأصناف كاختبارات تكاملية ضد قاعدة البيانات المعنية. تستخدم اختبارات MySQL حزمة Testcontainers لتشغيل قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres أداة Docker Compose لنفس الغرض.

## تجميع ملفات CSS

يوجد ملف `petclinic.css` في `src/main/resources/static/resources/css`. تم توليده من مصدر `petclinic.scss` مع دمجه مع مكتبة [Bootstrap](https://getbootstrap.com/). إذا أجريت تغييرات على ملف `scss`، أو قمت بترقية Bootstrap، فستحتاج إلى إعادة تجميع موارد CSS باستخدام بروفايل Maven المسمّى "css"، أي `./mvnw package -P css`. لا يوجد بروفايل بناء في Gradle لتجميع CSS.

## العمل مع Petclinic في بيئة التطوير IDE

### المتطلبات المسبقة

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (حزمة JDK كاملة، وليست JRE)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- بيئة التطوير التي تفضّلها
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متاحة، يظهر شعار m2 في نافذة `Help -> About`. إذا لم تكن موجودة، اتبع خطوات التثبيت [من هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project` ثم اختر الدليل الجذر للمستودع المستنسخ.

    بعد ذلك إمّا أن تُجري عملية البناء من سطر الأوامر `./mvnw generate-resources` أو تستخدم مُشغّل Eclipse (انقر يمينًا على المشروع ثم `Run As -> Maven install`) لتوليد ملفات CSS. شغّل الدالة الرئيسية للتطبيق بالنقر يمينًا عليها واختيار `Run As -> Java Application`.

1. داخل IntelliJ IDEA:

    من القائمة الرئيسية اختر `File -> Open` وحدد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. ثم انقر زر `Open`.

    - يتم توليد ملفات CSS من خلال بناء Maven. يمكنك توليدها من سطر الأوامر `./mvnw generate-resources` أو بالنقر يمينًا على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - يجب أن يتم إنشاء ضبط تشغيل باسم `PetClinicApplication` تلقائيًا إذا كنت تستخدم إصدار Ultimate حديث. وإلا فقم بتشغيل التطبيق عبر النقر يمينًا على الصنف الرئيسي `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

1. تصفّح تطبيق Petclinic

    زر [http://localhost:8080](http://localhost:8080) في متصفحك.

## هل تبحث عن شيء محدّد؟

| تهيئة Spring Boot | الفئة أو ملفات الخصائص (Java) |
|-------------------|-------------------------------|
| الصنف الرئيسي | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت Caching | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع وتفريعات Spring Petclinic المثيرة للاهتمام

الفرع "main" من Spring Petclinic في منظمة GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic)
هو التنفيذ "المرجعي" المعتمد على Spring Boot وThymeleaf. توجد
[تفريعات عديدة](https://spring-petclinic.github.io/docs/forks.html) ضمن منظمة GitHub
[spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام حزمة تقنيات مختلفة لبناء Pet Clinic، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع مفتوحة المصدر أخرى

من أفضل جوانب العمل على تطبيق Spring Petclinic أننا نحظى بفرصة التواصل المباشر مع العديد من المشاريع مفتوحة المصدر. لقد وجدنا أخطاء/واقترحنا تحسينات في مواضيع متنوعة مثل Spring وSpring Data وBean Validation وحتى Eclipse! وفي كثير من الحالات تم إصلاحها/تنفيذها خلال بضعة أيام فقط.
إليك قائمة ببعضها:

| الاسم | المشكلة |
|-------|---------|
| Spring JDBC: simplify usage of NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: simplify Maven dependencies and backward compatibility | [HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: provide more flexibility when working with JPQL queries | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

[متعقّب القضايا](https://github.com/spring-projects/spring-petclinic/issues) هو القناة المفضلة لإبلاغ الأخطاء، واقتراح الميزات، وتقديم طلبات السحب (Pull Requests).

لتقديم طلبات السحب، تتوفر تفضيلات المحرر في ملف [editor config](.editorconfig) لسهولة الاستخدام في معظم المحررات الشائعة. اقرأ المزيد وقم بتنزيل الإضافات من <https://editorconfig.org>. يجب أن تتضمن جميع الالتزامات (commits) سطر __Signed-off-by__ في نهاية رسالة الالتزام للإشارة إلى موافقة المساهم على شهادة منشئ البرمجيات (Developer Certificate of Origin).
لمزيد من التفاصيل، يُرجى الرجوع إلى تدوينة: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

تم إصدار تطبيق Spring PetClinic النموذجي بموجب الإصدار 2.0 من [رخصة أباتشي](https://www.apache.org/licenses/LICENSE-2.0).
