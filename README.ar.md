<div dir="rtl" lang="ar">

# تطبيق Spring PetClinic النموذجي [![حالة البناء](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![حالة البناء](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

اللغات: [English](README.md) | العربية

[![افتح في Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![افتح في GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring Petclinic عبر بعض المخططات

[اطّلع على العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

يمثل Spring Petclinic تطبيقًا مبنيًا باستخدام [Spring Boot](https://spring.io/guides/gs/spring-boot) ويمكن بناؤه باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك إنشاء ملف jar وتشغيله من سطر الأوامر (يجب أن يعمل على Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(على Windows، أو إذا كان الغلاف الخاص بك لا يوسّع نمط glob، فقد تحتاج إلى تحديد اسم ملف JAR صراحةً في نهاية الأمر.)

بعد ذلك، يمكنك الوصول إلى Petclinic عبر <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

كما يمكنك تشغيله مباشرةً من Maven باستخدام إضافة Spring Boot Maven. إذا فعلت ذلك، فسوف يلتقط التغييرات التي تجريها على المشروع فورًا (التغييرات على ملفات Java تتطلب أيضًا عملية ترجمة compile — وغالبًا ما يستخدم معظم المطوّرين IDE لهذا الغرض):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضّل استخدام Gradle، فيمكنك بناء التطبيق باستخدام `./gradlew build` ثم البحث عن ملف jar في `build/libs`.

## بناء حاوية (Container)

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك عميل Docker قيد التشغيل) باستخدام إضافة البناء الخاصة بـ Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حال وجدت خطأً/اقترحتَ تحسينًا لتطبيق Spring Petclinic

متتبع المشاكل متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## تهيئة قاعدة البيانات

في الإعداد الافتراضي، يستخدم Petclinic قاعدة بيانات داخل الذاكرة (H2) يتم ملؤها بالبيانات عند بدء التشغيل. واجهة h2 متاحة على `http://localhost:8080/h2-console`، ومن الممكن تفحّص محتوى قاعدة البيانات باستخدام عنوان `jdbc:h2:mem:<uuid>`. يتم طباعة قيمة UUID في وحدة التحكم عند بدء التشغيل.

يتوفر إعداد مشابه لكلٍ من MySQL وPostgreSQL إذا كنت تحتاج إلى تهيئة قاعدة بيانات مستمرة. لاحظ أنه كلما تغيّر نوع قاعدة البيانات، يجب تشغيل التطبيق بملف تعريف (Profile) مختلف: `spring.profiles.active=mysql` من أجل MySQL أو `spring.profiles.active=postgres` من أجل PostgreSQL. راجع [توثيق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية تعيين الملف التعريفي النشط.

يمكنك تشغيل MySQL أو PostgreSQL محليًا باستخدام أي مُثبّت يناسب نظام التشغيل لديك أو استخدام Docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

تتوفر وثائق إضافية لكلٍ من [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
و[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلاً من استخدام `docker` مباشرةً يمكنك أيضًا استخدام ملف `docker-compose.yml` المرفق لبدء حاويات قواعد البيانات. لدى كلٍ منها خدمة Service تحمل اسم ملف التعريف الخاص بـ Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

نوصي أثناء التطوير باستخدام تطبيقات الاختبار المُعدّة كدوال `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية بالإضافة إلى Spring Boot Devtools) و`MySqlTestApplication` و`PostgresIntegrationTests`. تم إعداد هذه التطبيقات بحيث يمكنك تشغيلها داخل بيئة التطوير IDE للحصول على تغذية راجعة سريعة، كما يمكنك تشغيل نفس الأصناف كاختبارات تكامل ضد قاعدة البيانات المقابلة. تستخدم اختبارات MySQL مكتبة Testcontainers لبدء قاعدة البيانات داخل حاوية Docker، بينما تستخدم اختبارات Postgres أداة Docker Compose للغرض نفسه.

## ترجمة ملفات CSS

يوجد ملف `petclinic.css` داخل `src/main/resources/static/resources/css`. تم توليد هذا الملف من المصدر `petclinic.scss` مع دمج مكتبة [Bootstrap](https://getbootstrap.com/). إذا أجريت تغييرات على ملفات `scss`، أو قمت بترقية Bootstrap، فستحتاج إلى إعادة ترجمة موارد CSS باستخدام ملف Maven التعريفي "css"، أي بتنفيذ الأمر: `./mvnw package -P css`. لا يوجد ملف تعريف مماثل في Gradle لترجمة CSS.

## العمل مع Petclinic في بيئة التطوير IDE

### المتطلبات المسبقة

يجب تثبيت العناصر التالية على نظامك:

- Java 17 أو أحدث (JDK كامل، وليس JRE)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- بيئة التطوير المفضلة لديك
  - Eclipse مع إضافة m2e. ملاحظة: عندما تكون m2e متاحة، يظهر رمز m2 في نافذة `Help -> About`. إذا لم تكن m2e موجودة، اتبع عملية التثبيت من [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. من سطر الأوامر نفّذ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

2. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project` ثم اختر المجلد الجذري للمستودع المستنسخ.

    بعد ذلك إمّا أن تبني عبر سطر الأوامر `./mvnw generate-resources` أو استخدم مشغّل Eclipse (انقر بالزر الأيمن على المشروع ثم `Run As -> Maven install`) لتوليد ملفات CSS. شغّل الدالة الرئيسية للتطبيق عبر النقر الأيمن عليها واختيار `Run As -> Java Application`.

3. داخل IntelliJ IDEA:

    من القائمة الرئيسية اختر `File -> Open` وحدّد ملف [pom.xml](pom.xml) الخاص بـ Petclinic. اضغط زر `Open`.

    - يتم توليد ملفات CSS من بناء Maven. يمكنك بناؤها من سطر الأوامر `./mvnw generate-resources` أو انقر بالزر الأيمن على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - ينبغي أن يتم إنشاء إعداد تشغيل باسم `PetClinicApplication` تلقائيًا إذا كنت تستخدم نسخة Ultimate حديثة. وإلا فقم بتشغيل التطبيق عبر النقر الأيمن على الصنف الرئيسي `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

4. انتقل إلى Petclinic

    زر [http://localhost:8080](http://localhost:8080) في المتصفح.

## هل تبحث عن شيء محدد؟

| تهيئة Spring Boot | الأصناف أو ملفات خصائص Java |
|--------------------|------------------------------|
| الصنف الرئيسي | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| التخزين المؤقت Caching | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع ومشتقات Spring Petclinic المثيرة للاهتمام

يُعد فرع "main" لمشروع Spring Petclinic ضمن منظمة GitHub [spring-projects](https://github.com/spring-projects/spring-petclinic) هو التطبيق "القياسي" المبني على Spring Boot وThymeleaf. توجد [عدد كبير من المشتقات (forks)](https://spring-petclinic.github.io/docs/forks.html) ضمن منظمة GitHub
[spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام مكدس تقني مختلف لتنفيذ Pet Clinic، فالرجاء الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع مفتوحة المصدر أخرى

من أفضل الجوانب في العمل على تطبيق Spring Petclinic أننا نمتلك فرصة للتفاعل المباشر مع العديد من المشاريع مفتوحة المصدر. لقد وجدنا أخطاء/واقترحنا تحسينات في مواضيع متنوعة مثل Spring وSpring Data وBean Validation وحتى Eclipse! في كثير من الحالات تم إصلاحها/تنفيذها خلال بضعة أيام فقط.
إليك قائمة بها:

| الاسم | المسألة |
|------|---------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven والتوافقية العكسية |[HV-790](https://hibernate.atlassian.net/browse/HV-790) و[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مرونة أكبر عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

[متعقّب المشاكل](https://github.com/spring-projects/spring-petclinic/issues) هو القناة المفضلة لتقارير الأخطاء وطلبات الميزات وتقديم طلبات السحب.

بالنسبة لطلبات السحب، تتوفر تفضيلات المحرر في ملف [editor config](.editorconfig) لسهولة الاستخدام في أشهر محررات النصوص. اقرأ المزيد وحمّل الإضافات عبر <https://editorconfig.org>. يجب أن تتضمّن جميع الالتزامات (Commits) عبارة __Signed-off-by__ في نهاية رسالة الالتزام للدلالة على موافقة المساهم على شهادة أصل المطوّر (DCO).
للتفاصيل الإضافية، يُرجى الرجوع إلى التدوينة: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

يتم إصدار تطبيق Spring PetClinic النموذجي بموجب النسخة 2.0 من [رخصة Apache](https://www.apache.org/licenses/LICENSE-2.0).

</div>
