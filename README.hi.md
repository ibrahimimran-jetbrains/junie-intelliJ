# स्प्रिंग पेटक्लिनिक नमूना एप्लिकेशन [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Gitpod में खोलें](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![GitHub Codespaces में खोलें](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

> भाषा: [English](README.md) | हिंदी

## कुछ आरेखों के साथ स्प्रिंग पेटक्लिनिक एप्लिकेशन को समझना

[प्रेज़ेंटेशन यहाँ देखें](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## पेटक्लिनिक को लोकल पर चलाएँ

स्प्रिंग पेटक्लिनिक एक [Spring Boot](https://spring.io/guides/gs/spring-boot) एप्लिकेशन है, जिसे [Maven](https://spring.io/guides/gs/maven/) या [Gradle](https://spring.io/guides/gs/gradle/) का उपयोग करके बनाया गया है। आप एक JAR फ़ाइल बना सकते हैं और कमांड लाइन से चला सकते हैं (यह Java 17 या उससे नए संस्करणों के साथ समान रूप से काम करता है):

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

(Windows पर, या यदि आपका शेल ग्लोब का विस्तार नहीं करता है, तो आपको अंत में JAR फ़ाइल का नाम स्पष्ट रूप से लिखना पड़ सकता है।)

इसके बाद आप Petclinic को <http://localhost:8080/> पर एक्सेस कर सकते हैं।

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

या आप इसे सीधे Maven से Spring Boot Maven प्लगइन का उपयोग कर चला सकते हैं। यदि आप ऐसा करते हैं, तो यह आपके द्वारा प्रोजेक्ट में किए गए बदलावों को तुरंत उठा लेगा (Java सोर्स फ़ाइलों में बदलाव के लिए कंपाइल भी आवश्यक है — अधिकांश लोग इसके लिए IDE का उपयोग करते हैं):

```bash
./mvnw spring-boot:run
```

> नोट: यदि आप Gradle का उपयोग करना पसंद करते हैं, तो आप `./gradlew build` से ऐप बना सकते हैं और JAR फ़ाइल `build/libs` में देख सकते हैं।

## कंटेनर बनाना

इस प्रोजेक्ट में `Dockerfile` नहीं है। आप Spring Boot build प्लगइन का उपयोग करके (यदि आपके पास docker डेमन है) एक कंटेनर इमेज बना सकते हैं:

```bash
./mvnw spring-boot:build-image
```

## यदि आपको Spring Petclinic के लिए कोई बग/सुधार सुझाना हो

हमारा इश्यू ट्रैकर [यहाँ उपलब्ध है](https://github.com/spring-projects/spring-petclinic/issues)।

## डेटाबेस कॉन्फ़िगरेशन

डिफ़ॉल्ट कॉन्फ़िगरेशन में, Petclinic एक इन-मेमोरी डेटाबेस (H2) का उपयोग करता है जो स्टार्टअप पर डेटा से भरा जाता है। H2 कंसोल `http://localhost:8080/h2-console` पर उपलब्ध है, और `jdbc:h2:mem:<uuid>` URL का उपयोग करके डेटाबेस की सामग्री देखी जा सकती है। UUID स्टार्टअप के समय कंसोल पर प्रिंट होता है।

यदि स्थायी डेटाबेस कॉन्फ़िगरेशन की आवश्यकता हो, तो MySQL और PostgreSQL के लिए समान सेटअप प्रदान किया गया है। ध्यान दें कि जब भी डेटाबेस प्रकार बदलता है, ऐप को अलग प्रोफ़ाइल के साथ चलाना होगा: MySQL के लिए `spring.profiles.active=mysql` या PostgreSQL के लिए `spring.profiles.active=postgres`। सक्रिय प्रोफ़ाइल सेट करने के बारे में अधिक जानकारी के लिए [Spring Boot दस्तावेज़](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) देखें।

आप अपने OS के लिए उपयुक्त इंस्टॉलर से लोकल पर MySQL या PostgreSQL शुरू कर सकते हैं या docker का उपयोग कर सकते हैं:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

या

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

अधिक दस्तावेज़ीकरण [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt) और [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt) के लिए उपलब्ध है।

साधारण `docker` के बजाय आप प्रदान किए गए `docker-compose.yml` फ़ाइल का भी उपयोग कर डेटाबेस कंटेनरों को शुरू कर सकते हैं। प्रत्येक के पास Spring प्रोफ़ाइल के नाम से एक सर्विस है:

```bash
docker compose up mysql
```

या

```bash
docker compose up postgres
```

## टेस्ट एप्लिकेशंस

डेवलपमेंट समय पर हम अनुशंसा करते हैं कि आप `PetClinicIntegrationTests` (डिफ़ॉल्ट H2 डेटाबेस और Spring Boot Devtools के साथ), `MySqlTestApplication` और `PostgresIntegrationTests` में सेट किए गए `main()` मेथड वाले टेस्ट एप्लिकेशंस का उपयोग करें। ये इस प्रकार सेट हैं कि आप तेज़ फीडबैक के लिए IDE में ऐप्स चला सकें और वही क्लासेज़ को संबंधित डेटाबेस के विरुद्ध इंटिग्रेशन टेस्ट के रूप में भी चला सकें। MySQL इंटिग्रेशन टेस्ट्स Testcontainers का उपयोग करते हैं और Postgres टेस्ट्स के लिए Docker Compose का उपयोग होता है।

## CSS को कम्पाइल करना

`src/main/resources/static/resources/css` में `petclinic.css` मौजूद है। यह `petclinic.scss` सोर्स से और [Bootstrap](https://getbootstrap.com/) लाइब्रेरी के साथ मिलाकर जेनरेट किया गया है। यदि आप `scss` में बदलाव करते हैं या Bootstrap अपग्रेड करते हैं, तो आपको Maven प्रोफ़ाइल "css" का उपयोग कर CSS संसाधनों को पुनः कम्पाइल करना होगा, जैसे `./mvnw package -P css`। CSS कम्पाइल करने के लिए Gradle में कोई प्रोफ़ाइल उपलब्ध नहीं है।

## अपने IDE में Petclinic के साथ काम करना

### आवश्यकताएँ

आपकी प्रणाली में निम्नलिखित चीज़ें इंस्टॉल होनी चाहिए:

- Java 17 या नया (पूर्ण JDK, केवल JRE नहीं)
- [Git कमांड लाइन टूल](https://help.github.com/articles/set-up-git)
- आपका पसंदीदा IDE
  - m2e प्लगइन के साथ Eclipse। नोट: जब m2e उपलब्ध होता है, तो `Help -> About` डायलॉग में m2 आइकन दिखता है। यदि m2e वहाँ नहीं है, तो [यहाँ](https://www.eclipse.org/m2e/) दिए इंस्टॉल प्रक्रिया का पालन करें।
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### चरण

1. कमांड लाइन पर चलाएँ:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Eclipse या STS के अंदर:

    `File -> Import -> Maven -> Existing Maven project` के माध्यम से प्रोजेक्ट खोलें, फिर क्लोन की गई रिपोज़िटरी की रूट डायरेक्टरी चुनें।

    फिर या तो कमांड लाइन पर `./mvnw generate-resources` चलाकर या Eclipse लॉन्चर (`प्रोजेक्ट पर राइट-क्लिक` और `Run As -> Maven install`) से CSS जेनरेट करें। एप्लिकेशन का मुख्य मेथड `Run As -> Java Application` चुनकर चलाएँ।

1. IntelliJ IDEA के अंदर:

    मुख्य मेनू में `File -> Open` चुनें और Petclinic के [pom.xml](pom.xml) को चुनें। `Open` बटन पर क्लिक करें।

    - CSS फाइलें Maven बिल्ड से जेनरेट होती हैं। आप इन्हें कमांड लाइन से `./mvnw generate-resources` चलाकर या `spring-petclinic` प्रोजेक्ट पर राइट-क्लिक करके `Maven -> Generates sources and Update Folders` से बना सकते हैं।

    - यदि आप हाल की Ultimate वर्ज़न का उपयोग कर रहे हैं तो `PetClinicApplication` नाम का एक रन कॉन्फ़िगरेशन आपके लिए पहले से बना होना चाहिए। अन्यथा, `PetClinicApplication` मुख्य क्लास पर राइट-क्लिक करके `Run 'PetClinicApplication'` चुनकर एप्लिकेशन चलाएँ।

1. Petclinic पर जाएँ

    अपने ब्राउज़र में [http://localhost:8080](http://localhost:8080) खोलें।

## क्या आप किसी विशेष चीज़ की तलाश में हैं?

| Spring Boot कॉन्फ़िगरेशन | क्लास या Java प्रॉपर्टी फाइलें |
|---------------------------|---|
| मुख्य क्लास | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
| प्रॉपर्टी फाइलें | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
| कैशिंग | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## दिलचस्प Spring Petclinic ब्रांचेज़ और फोर्क्स

[spring-projects](https://github.com/spring-projects/spring-petclinic) GitHub ऑर्ग में Spring Petclinic की "main" ब्रांच Spring Boot और Thymeleaf पर आधारित "कैनोनिकल" इम्प्लीमेंटेशन है। GitHub ऑर्ग [spring-petclinic](https://github.com/spring-petclinic) में [काफी फोर्क्स](https://spring-petclinic.github.io/docs/forks.html) हैं। यदि आप Pet Clinic को किसी अलग टेक्नोलॉजी स्टैक से इम्प्लीमेंट करना चाहते हैं, तो कृपया वहाँ समुदाय से जुड़ें।

## अन्य ओपन-सोर्स प्रोजेक्ट्स के साथ इंटरैक्शन

Spring Petclinic एप्लिकेशन पर काम करने का एक बेहतरीन हिस्सा यह है कि हमें कई ओपन सोर्स प्रोजेक्ट्स के साथ प्रत्यक्ष संपर्क में काम करने का अवसर मिलता है। हमने Spring, Spring Data, Bean Validation और यहाँ तक कि Eclipse जैसे विभिन्न विषयों पर बग पाए/सुधार सुझाए! कई मामलों में, उन्हें कुछ ही दिनों में ठीक/इम्प्लीमेंट कर दिया गया है।
यहाँ उनकी सूची है:

| नाम | इश्यू |
|-----|-------|
| Spring JDBC: NamedParameterJdbcTemplate के उपयोग को सरल बनाना | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) और [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: Maven डिपेंडेंसियों और बैकवर्ड कम्पैटिबिलिटी को सरल बनाना | [HV-790](https://hibernate.atlassian.net/browse/HV-790) और [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: JPQL क्वेरीज़ के साथ काम करते समय अधिक लचीलापन प्रदान करना | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## योगदान

बग रिपोर्ट्स, फीचर रिक्वेस्ट्स और पुल रिक्वेस्ट सबमिट करने के लिए [इश्यू ट्रैकर](https://github.com/spring-projects/spring-petclinic/issues) पसंदीदा चैनल है।

पुल रिक्वेस्ट्स के लिए, सामान्य टेक्स्ट एडिटर्स में आसान उपयोग हेतु [editor config](.editorconfig) में एडिटर प्रेफरेंसेज़ उपलब्ध हैं। इसके बारे में अधिक पढ़ें और प्लगइन्स <https://editorconfig.org> से डाउनलोड करें। सभी कमिट्स में अंत में __Signed-off-by__ ट्रेलर शामिल होना चाहिए, जो दर्शाता है कि योगदानकर्ता Developer Certificate of Origin से सहमत है।
अधिक विवरण के लिए ब्लॉग पोस्ट देखें: [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring)।

## लाइसेंस

Spring PetClinic नमूना एप्लिकेशन [Apache License](https://www.apache.org/licenses/LICENSE-2.0) के संस्करण 2.0 के अंतर्गत जारी किया गया है।
