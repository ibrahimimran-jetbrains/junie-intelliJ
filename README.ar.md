# تطبيق عيادة الحيوانات الأليفة من Spring (مستودع junie-intelliJ)

*ملاحظة: هذا تفرع من تطبيق Spring PetClinic الأصلي، والمستضاف الآن في مستودع junie-intelliJ.*

[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## فهم تطبيق Spring Petclinic من خلال بعض المخططات

[شاهد العرض التقديمي هنا](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## تشغيل Petclinic محليًا

Spring Petclinic هو تطبيق [Spring Boot](https://spring.io/guides/gs/spring-boot) تم بناؤه باستخدام [Maven](https://spring.io/guides/gs/maven/) أو [Gradle](https://spring.io/guides/gs/gradle/). يمكنك بناء ملف jar وتشغيله من سطر الأوامر (يجب أن يعمل بشكل جيد مع Java 17 أو أحدث):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(في نظام Windows، أو إذا كان shell الخاص بك لا يوسع الـ glob، قد تحتاج إلى تحديد اسم ملف JAR بشكل صريح في سطر الأوامر في النهاية.)

يمكنك بعد ذلك الوصول إلى Petclinic على <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

أو يمكنك تشغيله من Maven مباشرة باستخدام البرنامج المساعد Spring Boot Maven. إذا قمت بذلك، فسيلتقط التغييرات التي تجريها في المشروع على الفور (تتطلب التغييرات على ملفات مصدر Java تجميعًا أيضًا - معظم الناس يستخدمون IDE لهذا):

```bash
./mvnw spring-boot:run
```

> ملاحظة: إذا كنت تفضل استخدام Gradle، يمكنك بناء التطبيق باستخدام `./gradlew build` والبحث عن ملف jar في `build/libs`.

## بناء حاوية (Container)

لا يوجد ملف `Dockerfile` في هذا المشروع. يمكنك بناء صورة حاوية (إذا كان لديك خادم docker) باستخدام البرنامج المساعد لبناء Spring Boot:

```bash
./mvnw spring-boot:build-image
```

## في حالة العثور على خطأ/اقتراح تحسين لـ Spring Petclinic

متتبع المشكلات الخاص بنا متاح [هنا](https://github.com/spring-projects/spring-petclinic/issues).

## تكوين قاعدة البيانات

في تكوينه الافتراضي، يستخدم Petclinic قاعدة بيانات في الذاكرة (H2) يتم ملؤها عند بدء التشغيل بالبيانات. يتم عرض وحدة تحكم h2 على `http://localhost:8080/h2-console`، ومن الممكن فحص محتوى قاعدة البيانات باستخدام عنوان URL `jdbc:h2:mem:<uuid>`. يتم طباعة UUID عند بدء التشغيل على وحدة التحكم.

يتم توفير إعداد مماثل لـ MySQL و PostgreSQL إذا كانت هناك حاجة إلى تكوين قاعدة بيانات دائمة. لاحظ أنه كلما تغير نوع قاعدة البيانات، يحتاج التطبيق إلى التشغيل بملف تعريف مختلف: `spring.profiles.active=mysql` لـ MySQL أو `spring.profiles.active=postgres` لـ PostgreSQL. راجع [وثائق Spring Boot](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) لمزيد من التفاصيل حول كيفية تعيين ملف التعريف النشط.

يمكنك بدء تشغيل MySQL أو PostgreSQL محليًا باستخدام أي مثبت يعمل لنظام التشغيل الخاص بك أو استخدام docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

أو

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

يتم توفير مزيد من الوثائق لـ [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) و [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

بدلاً من استخدام `docker` العادي، يمكنك أيضًا استخدام ملف `docker-compose.yml` المتوفر لبدء تشغيل حاويات قاعدة البيانات. كل واحدة لديها خدمة تسمى باسم ملف تعريف Spring:

```bash
docker compose up mysql
```

أو

```bash
docker compose up postgres
```

## تطبيقات الاختبار

في وقت التطوير، نوصي باستخدام تطبيقات الاختبار المعدة كطرق `main()` في `PetClinicIntegrationTests` (باستخدام قاعدة بيانات H2 الافتراضية وأيضًا إضافة Spring Boot Devtools)، و `MySqlTestApplication` و `PostgresIntegrationTests`. تم إعدادها بحيث يمكنك تشغيل التطبيقات في IDE الخاص بك للحصول على تعليقات سريعة وأيضًا تشغيل نفس الفئات كاختبارات تكامل ضد قاعدة البيانات المعنية. تستخدم اختبارات تكامل MySQL Testcontainers لبدء تشغيل قاعدة البيانات في حاوية Docker، وتستخدم اختبارات Postgres Docker Compose للقيام بنفس الشيء.

## تجميع CSS

هناك ملف `petclinic.css` في `src/main/resources/static/resources/css`. تم إنشاؤه من مصدر `petclinic.scss`، مدمجًا مع مكتبة [Bootstrap](https://getbootstrap.com/). إذا قمت بإجراء تغييرات على `scss`، أو ترقية Bootstrap، فستحتاج إلى إعادة تجميع موارد CSS باستخدام ملف تعريف Maven "css"، أي `./mvnw package -P css`. لا يوجد ملف تعريف بناء لـ Gradle لتجميع CSS.

## العمل مع Petclinic في IDE الخاص بك

### المتطلبات الأساسية

يجب تثبيت العناصر التالية في نظامك:

- Java 17 أو أحدث (JDK كامل، وليس JRE)
- [أداة سطر أوامر Git](https://help.github.com/articles/set-up-git)
- IDE المفضل لديك
  - Eclipse مع البرنامج المساعد m2e. ملاحظة: عندما يكون m2e متاحًا، يوجد رمز m2 في مربع حوار `Help -> About`. إذا لم يكن m2e موجودًا، اتبع عملية التثبيت [هنا](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### الخطوات

1. في سطر الأوامر، قم بتشغيل:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

2. داخل Eclipse أو STS:

    افتح المشروع عبر `File -> Import -> Maven -> Existing Maven project`، ثم حدد الدليل الجذر للمستودع المستنسخ.

    ثم إما قم بالبناء على سطر الأوامر `./mvnw generate-resources` أو استخدم مشغل Eclipse (انقر بزر الماوس الأيمن على المشروع واختر `Run As -> Maven install`) لإنشاء CSS. قم بتشغيل الطريقة الرئيسية للتطبيق بالنقر بزر الماوس الأيمن عليها واختيار `Run As -> Java Application`.

3. داخل IntelliJ IDEA:

    في القائمة الرئيسية، اختر `File -> Open` وحدد ملف Petclinic [pom.xml](pom.xml). انقر على زر `Open`.

    - يتم إنشاء ملفات CSS من بناء Maven. يمكنك بناؤها على سطر الأوامر `./mvnw generate-resources` أو انقر بزر الماوس الأيمن على مشروع `spring-petclinic` ثم `Maven -> Generates sources and Update Folders`.

    - يجب أن يكون قد تم إنشاء تكوين تشغيل يسمى `PetClinicApplication` لك إذا كنت تستخدم إصدارًا Ultimate حديثًا. وإلا، قم بتشغيل التطبيق بالنقر بزر الماوس الأيمن على الفئة الرئيسية `PetClinicApplication` واختيار `Run 'PetClinicApplication'`.

4. انتقل إلى Petclinic

    قم بزيارة [http://localhost:8080](http://localhost:8080) في متصفحك.

## هل تبحث عن شيء معين؟

|تكوين Spring Boot | فئة أو ملفات خصائص Java  |
|--------------------------|---|
|الفئة الرئيسية | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|ملفات الخصائص | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
|التخزين المؤقت | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## فروع وتفرعات Spring Petclinic المثيرة للاهتمام

فرع Spring Petclinic "الرئيسي" في [spring-projects](https://github.com/spring-projects/spring-petclinic) على GitHub هو التنفيذ "القانوني" المستند إلى Spring Boot و Thymeleaf. هناك [العديد من التفرعات](https://spring-petclinic.github.io/docs/forks.html) في منظمة GitHub [spring-petclinic](https://github.com/spring-petclinic). إذا كنت مهتمًا باستخدام مجموعة تقنية مختلفة لتنفيذ Pet Clinic، فيرجى الانضمام إلى المجتمع هناك.

## التفاعل مع مشاريع أخرى مفتوحة المصدر

أحد أفضل الأجزاء في العمل على تطبيق Spring Petclinic هو أن لدينا الفرصة للعمل على اتصال مباشر مع العديد من مشاريع المصدر المفتوح. لقد وجدنا أخطاء/اقترحنا تحسينات في مواضيع مختلفة مثل Spring و Spring Data و Bean Validation وحتى Eclipse! في كثير من الحالات، تم إصلاحها/تنفيذها في غضون أيام قليلة فقط.
إليك قائمة بها:

| الاسم | المشكلة |
|------|-------|
| Spring JDBC: تبسيط استخدام NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) و [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: تبسيط تبعيات Maven والتوافق مع الإصدارات السابقة | [HV-790](https://hibernate.atlassian.net/browse/HV-790) و [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: توفير مزيد من المرونة عند العمل مع استعلامات JPQL | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## المساهمة

[متتبع المشكلات](https://github.com/spring-projects/spring-petclinic/issues) هو القناة المفضلة لتقارير الأخطاء وطلبات الميزات وتقديم طلبات السحب.

بالنسبة لطلبات السحب، تتوفر تفضيلات المحرر في [تكوين المحرر](.editorconfig) للاستخدام السهل في محررات النصوص الشائعة. اقرأ المزيد وقم بتنزيل البرامج المساعدة على <https://editorconfig.org>. يجب أن تتضمن جميع عمليات الالتزام مقطع __Signed-off-by__ في نهاية كل رسالة التزام للإشارة إلى أن المساهم يوافق على شهادة المطور الأصلية.
لمزيد من التفاصيل، يرجى الرجوع إلى منشور المدونة [مرحبًا DCO، وداعًا CLA: تبسيط المساهمات في Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## الترخيص

تم إصدار تطبيق Spring PetClinic النموذجي بموجب الإصدار 2.0 من [ترخيص Apache](https://www.apache.org/licenses/LICENSE-2.0).