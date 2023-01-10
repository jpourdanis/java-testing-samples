# Java application testing examples 

### BUILD

`mvn clean package -DskipTests`

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

- GUI test with Selenium (with Page Object pattern)

[src/test/java/com/pik/contact/gui/selenium/test/ContactsTest.java](src/test/java/com/pik/contact/gui/selenium/test/ContactsTest.java)

### RUN external API tests

> Before we start, open this in a new tab and let the container load. Takes a few mins. Longer for slower internet.

[![Try in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/jpourdanis/java-testing-samples)

1. Create an account to https://gorest.co.in/
2. Create an API Token
3. Run `mvn -DAPITOKEN=<Your-Token-here> surefire:test`
