package com.pik.contact.api.internal;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.pik.contact.domain.Contact;

import net.minidev.json.JSONObject;

import static com.jayway.restassured.RestAssured.*;

public class ContactApiTest {

        private static final Logger logger = LoggerFactory.getLogger(ContactApiTest.class);

        public static Integer newUserId = RandomUtils.nextInt(1000);
        public static String randomEmail = RandomStringUtils.randomAlphabetic(8) + "@mail.com";
        public static Contact newContact;

        @BeforeClass
        @DisplayName("Should create a contact.")
        public static void setup() {
                RestAssured.baseURI = "http://localhost:8080";

                newContact = new Contact("John", "John Pourdanis", "Software Engineer In Test", randomEmail,
                                "123 456 7890",
                                "jpourdanis");
                newContact.setId(newUserId.toString());

                JSONObject createUserVars = new JSONObject();
                createUserVars.put("id", newContact.getId());
                createUserVars.put("name", newContact.getName());
                createUserVars.put("fullName", newContact.getFullName());
                createUserVars.put("jobTitle", newContact.getJobTitle());
                createUserVars.put("email", newContact.getEmail());
                createUserVars.put("mobile", newContact.getMobile());
                createUserVars.put("skypeId", newContact.getSkypeId());

                String response = given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(createUserVars)
                                .when()
                                .post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(201)
                                .extract()
                                .response()
                                .asString();
                logger.info("The new contact id is: " + response);

        }

        @AfterClass
        @DisplayName("Should delete the contact.")
        public static void teardown() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newContact.getId())
                                .when()
                                .delete("/rest/contacts/{id}")
                                .then()
                                .assertThat()
                                .statusCode(204);
        }

        @Test
        @DisplayName("Should get all contacts.")
        public void test1() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200);
        }

        @Test
        @DisplayName("Should get contact details by id.")
        public void test2() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .queryParam("keyword", newContact.getId())
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("fullName", Matchers.contains(newContact.getFullName()))
                                .body("email", Matchers.contains(newContact.getEmail()));
        }

        @Test
        @DisplayName("Should get contact details by email.")
        public void test3() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .queryParam("keyword", newContact.getEmail())
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("fullName", Matchers.contains(newContact.getFullName()))
                                .body("email", Matchers.contains(newContact.getEmail()));
        }

        @Test
        @DisplayName("Should update the name of a contact.")
        public void test4() {
                String fullNameToUpdate = "John Pourdanopoulos";
                Contact contactToUpdate = new Contact(null, fullNameToUpdate, null, null, null, null);
                newContact.updateWith(contactToUpdate);

                JSONObject updateContactVars = new JSONObject();
                updateContactVars.put("name", newContact.getName());
                updateContactVars.put("fullName", newContact.getFullName());
                updateContactVars.put("jobTitle", newContact.getJobTitle());
                updateContactVars.put("email", newContact.getEmail());
                updateContactVars.put("mobile", newContact.getMobile());
                updateContactVars.put("skypeId", newContact.getSkypeId());

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newContact.getId())
                                .body(updateContactVars)
                                .when()
                                .put("/rest/contacts/{id}")
                                .then()
                                .assertThat()
                                .statusCode(202)
                                .body("fullName", Matchers.equalTo(fullNameToUpdate))
                                .body("email", Matchers.equalTo(newContact.getEmail()));
        }

        @Test
        @DisplayName("Should fail to create a contact with null name.")
        public void test5() {

                JSONObject createContactVars = new JSONObject();
                createContactVars.put("name", null);
                createContactVars.put("fullName", newContact.getFullName());
                createContactVars.put("jobTitle", newContact.getJobTitle());
                createContactVars.put("email", newContact.getEmail());
                createContactVars.put("mobile", newContact.getMobile());
                createContactVars.put("skypeId", newContact.getSkypeId());

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(createContactVars)
                                .when()
                                .post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(400);

        }

        @Test
        @DisplayName("Should fail to create a contact with invalid email.")
        public void test6() {

                JSONObject createContactVars = new JSONObject();
                createContactVars.put("name", newContact.getName());
                createContactVars.put("fullName", newContact.getFullName());
                createContactVars.put("jobTitle", newContact.getJobTitle());
                createContactVars.put("email", "testMail");
                createContactVars.put("mobile", newContact.getMobile());
                createContactVars.put("skypeId", newContact.getSkypeId());

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(createContactVars)
                                .when()
                                .post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(400);

        }

        @Test
        @DisplayName("Should fail to create a contact with invalid number.")
        public void test7() {

                JSONObject createContactVars = new JSONObject();
                createContactVars.put("name", newContact.getName());
                createContactVars.put("fullName", newContact.getFullName());
                createContactVars.put("jobTitle", newContact.getJobTitle());
                createContactVars.put("email", newContact.getEmail());
                createContactVars.put("mobile", "123932993293");
                createContactVars.put("skypeId", newContact.getSkypeId());

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(createContactVars)
                                .when()
                                .post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(400);

        }

        @Test
        @DisplayName("Should fail to create a contact with invalid skype id.")
        public void test8() {

                JSONObject createContactVars = new JSONObject();
                createContactVars.put("name", newContact.getName());
                createContactVars.put("fullName", newContact.getFullName());
                createContactVars.put("jobTitle", newContact.getJobTitle());
                createContactVars.put("email", newContact.getEmail());
                createContactVars.put("mobile", newContact.getMobile());
                createContactVars.put("skypeId", "test");

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(createContactVars)
                                .when()
                                .post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(400);

        }

}
