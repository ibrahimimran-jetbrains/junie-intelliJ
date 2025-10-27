# تطبيق Spring PetClinic النموذجي [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[English](/README.md) | [العربية](/README.ar.md)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring Petclinic عبر بعض المخططات

[شاهد العرض هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

تطبيق Spring Petclinic هو تطبيق [Spring Boot](https://spring.io/guides/gs/spring-boot) مبني باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك إنشاء ملف jar وتشغيله من سطر الأوامر (يعمل بشكل جيد مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على Windows، أو إذا كان الغلاف لديك لا يوسّع نمط glob، قد تحتاج إلى تحديد اسم ملف JAR صراحةً في نهاية الأمر.)

بعد ذلك يمكنك الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

أو يمكنك تشغيله مباشرةً من Maven باستخدام إضافة Spring Boot لميفن. إذا فعلت ذلك فسيقوم بالتقاط التغييرات التي تجريها في المشروع فورًا (التغييرات على ملفات Java تتطلب أيضًا عملية compile — معظم المطورين يستخدمون IDE لهذا):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضّل استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` والبحث عن ملف jar في `build/libs`.

## بناء حاوية Container

لا يوجد `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك خادم Docker) باستخدام إضافة البناء الخاصة بـ Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدت خطأ/اقتراح تحسين

متعقّب القضايا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## إعداد قاعدة البيانات

في الإعداد الافتراضي يستخدم Petclinic قاعدة بيانات داخل الذاكرة (H2) ويتم تهيئتها بالبيانات عند الإقلاع. واجهة H2 متاحة على `http://localhost:8080/h2-console`، ومن الممكن تفحص محتوى قاعدة البيانات باستخدام عنوان `jdbc:h2:mem:<uuid>`. يتم طباعة الـ UUID عند الإقلاع في وحدة التحكم.

يتوفر إعداد مشابه لكلٍ من MySQL وPostgreSQL إذا كانت هناك حاجة إلى قاعدة بيانات دائمة. لاحظ أنه كلما تغيّر نوع قاعدة البيانات يجب تشغيل التطبيق بملف تعريف مختلف: `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [توثيق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية ضبط الملف النشِط.

يمكنك تشغيل MySQL أو PostgreSQL محليًا بأي مُثبّت يناسب نظامك أو استخدام docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

تتوفر وثائق إضافية لـ [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلاً من استخدام `docker` مباشرةً يمكنك أيضًا استخدام ملف `docker-compose.yml` المرفق لتشغيل حاويات قواعد البيانات. لكلٍ واحد خدمة تحمل اسم ملف التعريف الخاص بـ Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

نوصي وقت التطوير باستخدام تطبيقات الاختبار المُعدة كطرائق `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية وإضافة Spring Boot Devtools)، و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه بحيث يمكنك تشغيل التطبيقات في بيئة التطوير للحصول على تغذية راجعة سريعة وتشغيل نفس الأصناف كاختبارات تكامل ضد قاعدة البيانات المعنية. تستخدم اختبارات تكامل MySql مكتبة Testcontainers لتشغيل قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres أداة Docker Compose لنفس الهدف.

## ترجمة ملفات CSS

يوجد ملف `petclinic.css` في `src/main/resources/static/resources/css`. تم توليده من المصدر `petclinic.scss` مع مكتبة [Bootstrap](https://getbootstrap.com/). إذا أجريتَ تغييرات على ملفات `scss`، أو حدّثت Bootstrap، فستحتاج إلى إعادة ترجمة موارد CSS باستخدام ملف Maven التعريفي "css" عبر الأمر `./mvnw package -P css`. لا يوجد ملف تعريف بناء مماثل في Gradle لعملية ترجمة CSS.

## العمل على Petclinic داخل بيئة التطوير (IDE)

### المتطلبات المسبقة

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (JDK كامل وليس JRE)
- [أداة Git لسطر الأوامر](https://help.github.com/articles/set-up-git)
- بيئة التطوير المفضلة لديك
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متاحة سيظهر رمز m2 في مربع الحوار `Help -> About`. إذا لم تكن موجودة فاتبع عملية التثبيت [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project` ثم اختر المجلد الجذري للمستودع المستنسَخ.

    بعد ذلك إمّا أن تبني عبر سطر الأوامر `./mvnw generate-resources` أو تستخدم مُطلِق Eclipse (انقر بزر الفأرة الأيمن على المشروع ثم `Run As -> Maven install`) لتوليد ملفات CSS. شغّل الطريقة الرئيسة للتطبيق بالنقر الأيمن على الصنف واختيار `Run As -> Java Application`.

1. داخل IntelliJ IDEA:

    من القائمة الرئيسية اختر `File -> Open` وحدد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. ثم انقر زر `Open`.

    - يتم توليد ملفات CSS من بناء Maven. يمكنك توليدها من سطر الأوامر `./mvnw generate-resources` أو بالنقر الأيمن على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - يجب أن يتم إنشاء إعداد تشغيل باسم `PetClinicApplication` تلقائيًا إذا كنت تستخدم نسخة Ultimate حديثة. وإلا فقم بتشغيل التطبيق بالنقر الأيمن على الصنف الرئيسي `PetClinicApplication` ثم اختيار `Run 'PetClinicApplication'`.

1. انتقل إلى Petclinic

    زر [http://localhost:8080](http://localhost:8080) في متصفحك.

## هل تبحث عن شيء بعينه؟

| إعدادات Spring Boot | الأصناف أو ملفات الخصائص |
|----------------------|---|
| الصنف الرئيسي | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت Caching | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع ومشاريع Spring Petclinic المثيرة للاهتمام

فرع "main" الخاص بـ Spring Petclinic في منظمة GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic) هو التنفيذ "القياسي" المبني على Spring Boot وThymeleaf. توجد [عدد من التفرعات](https://spring-petclinic.github.io/docs/forks.html) في منظمة GitHub [spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام تقنيات مختلفة لتنفيذ Pet Clinic، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع مفتوحة المصدر أخرى

من أفضل جوانب العمل على تطبيق Spring Petclinic أننا نملك فرصة العمل بتواصل مباشر مع العديد من مشاريع المصدر المفتوح. لقد وجدنا أخطاء/اقترحنا تحسينات في مواضيع متعددة مثل Spring وSpring Data وBean Validation وحتى Eclipse! في كثير من الحالات تم إصلاحها/تنفيذها خلال أيام قليلة فقط. هنا قائمة بها:

| الاسم | القضية |
|------|-------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven والتوافقية العكسية |[HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مرونة أكبر عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

يُفضل استخدام [متعقب القضايا](https://github.com/spring-projects/spring-petclinic/issues) للإبلاغ عن الأخطاء وطلبات الميزات وتقديم طلبات السحب.

لتقديم طلبات السحب، تتوفر تفضيلات المحرر في [editor config](.editorconfig) لسهولة الاستخدام في محررات النصوص الشائعة. اقرأ المزيد وحمّل الإضافات على <https://editorconfig.org>. يجب أن تتضمن جميع الالتزامات سطر __Signed-off-by__ في نهاية رسالة الالتزام للإشارة إلى موافقة المساهم على شهادة مطور البرمجيات المفتوحة.
لمزيد من التفاصيل، يرجى الرجوع إلى تدوينة المدونة [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

يتم إصدار تطبيق Spring PetClinic النموذجي تحت رخصة [Apache الإصدار 2.0](https://www.apache.org/licenses/LICENSE-2.0).