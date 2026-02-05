# تطبيق Spring PetClinic النموذجي [![حالة البناء](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![حالة البناء](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

اللغات: [العربية](README.ar.md) | [English](README.md)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring PetClinic عبر بعض المخططات

[اطّلع على العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

تطبيق Spring Petclinic هو تطبيق [Spring Boot](https://spring.io/guides/gs/spring-boot) بُني باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك بناء ملف jar وتشغيله من سطر الأوامر (يجب أن يعمل بشكل جيد مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على Windows، أو إذا لم يقم الصدَفة بتوسيع النمط النَجمي تلقائيًا، فقد تحتاج إلى تحديد اسم ملف JAR صراحةً في نهاية الأمر.)

بعدها يمكنك الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

أو يمكنك تشغيله مباشرةً من Maven باستخدام إضافة Spring Boot Maven. إذا فعلت ذلك، فسيكتشف التغييرات التي تُجريها في المشروع مباشرةً (التغييرات على ملفات مصدر Java تتطلب إعادة ترجمة أيضًا — أغلب المطورين يستخدمون بيئة تطوير متكاملة لهذه المهمة):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضّل استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` ثم العثور على ملف jar في `build/libs`.

## بناء حاوية (Container)

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك خادوم Docker) باستخدام إضافة البناء الخاصة بـ Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدت خطأ/أو لديك اقتراح تحسين لتطبيق Spring Petclinic

متعقّب القضايا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## ضبط قاعدة البيانات

في الإعداد الافتراضي، يستخدم Petclinic قاعدة بيانات داخل الذاكرة (H2) يتم ملؤها بالبيانات عند الإقلاع. يتم كشف لوحة تحكم h2 على `http://localhost:8080/h2-console`، ومن الممكن تفقد محتوى قاعدة البيانات باستخدام عنوان `jdbc:h2:mem:<uuid>`. يتم طباعة قيمة UUID عند الإقلاع في وحدة التحكم.

يوفَّر إعداد مشابه لكلٍ من MySQL وPostgreSQL إذا كانت هناك حاجة إلى قاعدة بيانات مستمرة. لاحظ أنه كلما تغيّر نوع قاعدة البيانات، يجب تشغيل التطبيق بملف إعداد (Profile) مختلف: استخدم `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [وثائق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية تعيين الملف الفعّال.

يمكنك تشغيل MySQL أو PostgreSQL محليًا بأي مُثبّت مناسب لنظام التشغيل لديك أو استخدام Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

تتوفر وثائق إضافية لكلٍ من [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدل استخدام `docker` مباشرةً يمكنك أيضًا استخدام الملف `docker-compose.yml` المُتاح لتشغيل حاويات قواعد البيانات. كل واحد منها لديه خدمة تحمل اسم ملف الإعداد Spring profile:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

نوصي أثناء التطوير باستخدام تطبيقات الاختبار المُعَدّة كدوال `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية مع إضافة Spring Boot Devtools)، و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه التطبيقات بحيث يمكنك تشغيلها في بيئة التطوير للحصول على تغذية راجعة سريعة وتشغيل نفس الأصناف كاختبارات تكامل ضد قاعدة البيانات المقابلة. تستخدم اختبارات تكامل MySQL مكتبة Testcontainers لبدء قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres أداة Docker Compose للغرض نفسه.

## ترجمة ملفات CSS

يوجد ملف `petclinic.css` في `src/main/resources/static/resources/css`. تم توليده من المصدر `petclinic.scss` مع دمج مكتبة [Bootstrap](https://getbootstrap.com/). إذا أجريت تغييرات على ملفات `scss`، أو قمت بترقية Bootstrap، فستحتاج إلى إعادة ترجمة موارد CSS باستخدام بروفايل Maven "css"، أي: `./mvnw package -P css`. لا يوجد بروفايل بناء مماثل لـ Gradle لترجمة CSS.

## العمل على Petclinic في بيئة التطوير (IDE)

### المتطلبات الأساسية

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (مجموعة تطوير JDK كاملة، وليس JRE فقط)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- بيئة التطوير المفضلة لديك
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متوفرة، سترى أيقونة m2 في نافذة `Help -> About`. إذا لم تكن موجودة، اتبع عملية التثبيت من [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفِّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project`، ثم اختر الدليل الجذري للمستودع المستنسخ.

    بعد ذلك إمّا أن تبني عبر سطر الأوامر `./mvnw generate-resources` أو استخدم مشغّل Eclipse (انقر بالزر الأيمن على المشروع ثم `Run As -> Maven install`) لتوليد ملفات CSS. شغّل الدالة الرئيسية للتطبيق عبر النقر بالزر الأيمن عليها واختيار `Run As -> Java Application`.

1. داخل IntelliJ IDEA:

    من القائمة الرئيسية، اختر `File -> Open` وحدد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. ثم انقر زر `Open`.

    - يتم توليد ملفات CSS من خلال بناء Maven. يمكنك توليدها عبر سطر الأوامر `./mvnw generate-resources` أو النقر بالزر الأيمن على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - يجب أن تكون هناك تهيئة تشغيل باسم `PetClinicApplication` لديك تلقائيًا إذا كنت تستخدم إصدار Ultimate حديث. خلاف ذلك، شغّل التطبيق عبر النقر بالزر الأيمن على الصنف الرئيسي `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

1. تصفّح Petclinic

    زر [http://localhost:8080](http://localhost:8080) في المتصفح.

## هل تبحث عن شيء بعينه؟

| إعدادات Spring Boot | ملفات الأصناف أو الخصائص |
|---------------------|----------------------------|
| الصنف الرئيسي | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت (Caching) | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع وتفرعات Spring Petclinic المثيرة للاهتمام

الفرع "main" من Spring Petclinic ضمن منظمة GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic) هو التطبيق "المرجعي" المبني على Spring Boot وThymeleaf. هناك [عدد غير قليل من التفرعات](https://spring-petclinic.github.io/docs/forks.html) ضمن منظمة GitHub [spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام حزمة تقنية مختلفة لتنفيذ Pet Clinic، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع البرمجيات الحرة الأخرى

أحد أفضل جوانب العمل على تطبيق Spring Petclinic هو أننا نحصل على فرصة للتواصل المباشر مع العديد من مشاريع المصدر المفتوح. لقد وجدنا أخطاء/واقترحنا تحسينات في مواضيع متعددة مثل Spring وSpring Data وBean Validation وحتى Eclipse! في العديد من الحالات، جرى إصلاحها/تنفيذها خلال بضعة أيام فقط. إليك قائمة ببعضها:

| الاسم | القضية |
|------|--------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven والتوافقية العكسية | [HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مرونة أكبر عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

[متعقّب القضايا](https://github.com/spring-projects/spring-petclinic/issues) هو القناة المفضّلة لتقارير الأخطاء وطلبات الميزات وإرسال طلبات الدمج (Pull Requests).

بالنسبة لطلبات الدمج، تفضيلات المحرر متوفرة في ملف [editor config](.editorconfig) لتسهيل استخدامها في أشهر المحررات. اقرأ المزيد وحمّل الإضافات من <https://editorconfig.org>. يجب أن تتضمن جميع عمليات الدمج سطر __Signed-off-by__ في نهاية رسالة الالتزام (commit) للإشارة إلى موافقة المساهم على شهادة مطوّر البرمجيات (Developer Certificate of Origin). لمزيد من التفاصيل، يرجى الرجوع إلى تدوينة المدونة: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الرخصة

تطبيق Spring PetClinic النموذجي مُتاح بموجب الإصدار 2.0 من [رخصة Apache](https://www.apache.org/licenses/LICENSE-2.0).
