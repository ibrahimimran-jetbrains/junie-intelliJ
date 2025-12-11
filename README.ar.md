# تطبيق Spring PetClinic التجريبي

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

هذه الصفحة هي ترجمة عربية لملف README الأصلي لمشروع Spring PetClinic. تم الحفاظ على أوامر الأوامر البرمجية وروابطها كما هي. للمحتوى الأصلي باللغة الإنجليزية، راجع ملف README.md.

- اللغات: [English](README.md) | العربية

## فهم تطبيق Spring Petclinic من خلال بعض المخططات

[شاهد العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

تطبيق Spring Petclinic هو تطبيق [Spring Boot](https://spring.io/guides/gs/spring-boot) مبني باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك بناء ملف jar وتشغيله من سطر الأوامر (يجب أن يعمل بشكل جيد مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على نظام Windows، أو إذا كان غلاف الأوامر لا يتعرف على النمط النجمي، قد تحتاج إلى تحديد اسم ملف JAR صراحةً في نهاية الأمر.)

بعد ذلك يمكنك الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

أو يمكنك تشغيله مباشرةً باستخدام Maven عبر إضافة Spring Boot Maven. عند القيام بذلك، سيلتقط التطبيق التغييرات التي تجريها فورًا (التغييرات على ملفات Java تتطلب إعادة ترجمة أيضًا — معظم المطورين يستخدمون بيئة تطوير متكاملة لهذا الغرض):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا فضّلت استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` ثم البحث عن ملف jar في `build/libs`.

## بناء حاوية

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك خادوم Docker) باستخدام إضافة البناء الخاصة بـ Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدتَ خطأ/لديك اقتراح لتحسين Spring Petclinic

متعقّب القضايا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## إعداد قاعدة البيانات

في الإعداد الافتراضي، يستخدم Petclinic قاعدة بيانات ضمن الذاكرة (H2) ويتم تعبئتها بالبيانات عند الإقلاع. تتوافر واجهة H2 على `http://localhost:8080/h2-console`، ومن الممكن فحص محتوى قاعدة البيانات باستخدام المسار `jdbc:h2:mem:<uuid>`. يتم طباعة قيمة UUID في سجل التشغيل عند الإقلاع.

يتوفر إعداد مشابه لكلٍ من MySQL وPostgreSQL إذا كنت تحتاج إلى قاعدة بيانات دائمة. لاحظ أنه في كل مرة تغيّر فيها نوع قاعدة البيانات، يجب تشغيل التطبيق بملف تعريف مختلف: `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [توثيق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية تعيين ملف التعريف النشط.

يمكنك تشغيل MySQL أو PostgreSQL محليًا باستخدام أي مُثبّت مناسب لنظامك أو استخدام Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

تتوفر توثيقات إضافية لكلٍ من [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلاً من استخدام `docker` مباشرةً يمكنك أيضًا استخدام الملف `docker-compose.yml` المرفق لتشغيل حاويات قواعد البيانات. لكل قاعدة خدمة باسم يطابق ملف تعريف Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

نوصي أثناء التطوير باستخدام تطبيقات الاختبار المعدّة كطرائق `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية وإضافة Spring Boot Devtools أيضًا)، و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه التطبيقات بحيث يمكنك تشغيلها في بيئة التطوير للحصول على تغذية راجعة سريعة، وكذلك تشغيل نفس الأصناف كاختبارات تكامل مقابل قاعدة البيانات المعنية. تستخدم اختبارات MySql مكتبة Testcontainers لتشغيل قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres Docker Compose لنفس الغرض.

## ترجمة ملفات CSS

يوجد ملف `petclinic.css` في `src/main/resources/static/resources/css`. تم توليده من المصدر `petclinic.scss` ومكتبة [Bootstrap](https://getbootstrap.com/). إذا أجريت تغييرات على ملفات `scss` أو قمت بترقية Bootstrap، فستحتاج إلى إعادة ترجمة موارد CSS باستخدام ملف Maven التعريفي "css" أي: `./mvnw package -P css`. لا يوجد ملف تعريف مقابل في Gradle لترجمة CSS.

## العمل مع Petclinic في بيئة التطوير IDE

### المتطلبات المسبقة

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (JDK كامل، وليس JRE)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- بيئة التطوير المفضلة لديك
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متوفرة سترى أيقونة m2 في نافذة `Help -> About`. إن لم تكن موجودة فاتبع عملية التثبيت من [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project` ثم اختر الدليل الجذري للمستودع المستنسخ.

    بعد ذلك إمّا ابنِ عبر سطر الأوامر `./mvnw generate-resources` أو استخدم مشغّل Eclipse (انقر يمينًا على المشروع ثم `Run As -> Maven install`) لتوليد CSS. شغّل الطريقة الرئيسية للتطبيق بالنقر يمينًا على الصنف واختيار `Run As -> Java Application`.

1. داخل IntelliJ IDEA:

    من القائمة الرئيسية اختر `File -> Open` ثم حدّد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. انقر زر `Open`.

    - تُولَّد ملفات CSS من بناء Maven. يمكنك توليدها من سطر الأوامر `./mvnw generate-resources` أو بالنقر يمينًا على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - ينبغي أن يتم إنشاء تهيئة تشغيل باسم `PetClinicApplication` تلقائيًا إذا كنت تستخدم نسخة Ultimate حديثة. وإلا فشغّل التطبيق بالنقر يمينًا على الصنف الرئيسي `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

1. الانتقال إلى Petclinic

    زر [http://localhost:8080](http://localhost:8080) في متصفحك.

## هل تبحث عن شيء محدد؟

| إعدادات Spring Boot | الأصناف أو ملفات الخصائص |
|----------------------|---------------------------|
| الصنف الرئيسي | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع ونسخ Petclinic المثيرة للاهتمام

إن الفرع "main" لمشروع Spring Petclinic في منظمة GitHub التابعة لـ [spring-projects](https://github.com/spring-projects/spring-petclinic) هو التطبيق "المرجعي" المعتمد على Spring Boot وThymeleaf. هناك [عدد غير قليل من النسخ المتفرعة](https://spring-petclinic.github.io/docs/forks.html) في منظمة GitHub [spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام حزمة تقنية مختلفة لتنفيذ العيادة البيطرية، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع البرمجيات الحرة الأخرى

من أفضل الأمور في العمل على تطبيق Spring Petclinic أننا نملك فرصة للتعامل المباشر مع العديد من مشاريع البرمجيات الحرة. لقد وجدنا أخطاءً/اقترحنا تحسينات في مواضيع متنوعة مثل Spring وSpring Data وBean Validation وحتى Eclipse! في كثير من الحالات، تم إصلاحها/تنفيذها خلال بضعة أيام فقط. إليك قائمة ببعضها:

| الاسم | القضية |
|------|-------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven والتوافقية العكسية |[HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مرونة أكبر عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

مُتعقّب القضايا هو القناة المفضلة لتبليغ الأخطاء، طلبات الميزات، وإرسال طلبات الدمج.

بالنسبة لطلبات الدمج، توجد تفضيلات للمحرر في ملف [editor config](.editorconfig) لسهولة الاستخدام في أشهر المحررات. اقرأ المزيد وحمّل الإضافات من <https://editorconfig.org>. يجب أن تتضمن جميع الالتزامات سطر __Signed-off-by__ في نهاية رسالة الالتزام للدلالة على موافقة المساهم على شهادة المطور. لمزيد من التفاصيل، يُرجى الاطلاع على التدوينة: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

يُصدر تطبيق Spring PetClinic التجريبي تحت رخصة [Apache License](https://www.apache.org/licenses/LICENSE-2.0) الإصدار 2.0.
