# Java application testing examples for meetup : https://www.meetup.com/Serrai-Software-Development-Meetup/events/257786866/

### BUILD

mvn clean package

### RUN

You can launch the Java server within your IDE, by running the Application class at the root of your application's Java package.
As it is a simple "Main" class, it is the easiest and quickest way to run and debug the application.
If you prefer to use Maven, you can also run your application by typing:

`mvn spring-boot:run`

The application will be available on `http://localhost:8080`

Open API doc with swagger UI will be available on `http://localhost:8080/swagger-ui/index.html#/`

### TESTS examples

Application is tested using different kids of test:

- Unit

[src/test/java/com/pik/contact/unit/ContactTest.java](src/test/java/com/pik/contact/unit/ContactTest.java)

- Unit with mocks

[src/test/java/com/pik/contact/service/unit/ContactServiceTest.java](src/test/java/com/pik/contact/service/unit/ContactServiceTest.java)

- Integration test

[src/test/java/com/pik/contact/service/integration/ContactServiceTest.java](src/test/java/com/pik/contact/service/integration/ContactServiceTest.java)

- Spring mvc test for REST endpoint aka API test

[src/test/java/com/pik/contact/api/ContactControllerTest.java](src/test/java/com/pik/contact/api/ContactControllerTest.java)

- Cucumber acceptance test

[src/test/java/com/pik/contact/cucumber/RunCukesTest.java](src/test/java/com/pik/contact/cucumber/RunCukesTest.java)
[src/test/resources/com/pik/contact/cucumber/contacts.feature](src/test/resources/com/pik/contact/cucumber/contacts.feature)

- GUI test with Selenium (with Page Object pattern)

[src/test/java/com/pik/contact/gui/selenium/test/ContactsTest.java](src/test/java/com/pik/contact/gui/selenium/test/ContactsTest.java)
