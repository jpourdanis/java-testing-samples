package com.pik.contact.api.internal;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.specification.RequestSpecification;
import com.pik.contact.domain.Contact;

import net.minidev.json.JSONObject;

import static com.jayway.restassured.RestAssured.*;

public class ContactApiTest {
        public static Integer newUserId = RandomUtils.nextInt(1000);
        public static Contact newContact;
        public static String randomEmail = RandomStringUtils.randomAlphabetic(8) + "@mail.com";

        @BeforeClass
        @DisplayName("Should create a contact.")
        public static void setup() {
                RestAssured.baseURI = "http://localhost:8080";

                RequestSpecification request = RestAssured.given();

                request.header(new Header("Content-type", "application/json"));

                newContact = new Contact("John", "John Pourdanis", "Software Engineer In Test", randomEmail,
                                "123 456 7890",
                                "jpourdanis");

                JSONObject createUserVars = new JSONObject();
                createUserVars.put("id", newUserId.toString());
                createUserVars.put("name", newContact.getName());
                createUserVars.put("fullName", newContact.getFullName());
                createUserVars.put("jobTitle", "Software Engineer In Test");
                createUserVars.put("email", randomEmail);
                createUserVars.put("mobile", "123 456 7890");
                createUserVars.put("skypeId", "jpourdanis");

                request.body(createUserVars);

                String response = request.post("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(201)
                                .extract()
                                .response()
                                .asString();
                System.out.println("The new contact id is: " + response);
        }

        @Test
        @DisplayName("Should get all contact.")
        public void testA() {
                given()
                                .header(new Header("Content-type", "application/json"))
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200);
        }

        @Test
        @DisplayName("Should get contact details by id.")
        public void testB() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .queryParam("keyword", newUserId)
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("fullName", Matchers.contains("John Pourdanis"))
                                .body("email", Matchers.contains(randomEmail));
        }

        @Test
        @DisplayName("Should get contact details by email.")
        public void testC() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .queryParam("keyword", randomEmail)
                                .when()
                                .get("/rest/contacts")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("fullName", Matchers.contains("John Pourdanis"))
                                .body("email", Matchers.contains(randomEmail));
        }

        @Test
        @DisplayName("Should update the name of a contact.")
        public void testD() {
                String fullNameToUpdate = "John Pourdanopoulos";
                Contact contactToUpdate = new Contact(null, fullNameToUpdate, null, null, null, null);
                newContact.updateWith(contactToUpdate);

                JSONObject updateContactVars = new JSONObject();
                updateContactVars.put("name", newContact.getName());
                updateContactVars.put("fullName", newContact.getFullName());
                updateContactVars.put("jobTitle", "Software Engineer In Test");
                updateContactVars.put("email", randomEmail);
                updateContactVars.put("mobile", "123 456 7890");
                updateContactVars.put("skypeId", "jpourdanis");

                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newUserId)
                                .body(updateContactVars.toJSONString())
                                .when()
                                .put("/rest/contacts/{id}")
                                .then()
                                .assertThat()
                                .statusCode(202)
                                .body("fullName", Matchers.equalTo(fullNameToUpdate))
                                .body("email", Matchers.equalTo(randomEmail));
        }

        @AfterClass
        @DisplayName("Should delete the contact.")
        public static void teardown() {
                given()
                                .headers(
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newUserId)
                                .when()
                                .delete("/rest/contacts/{id}")
                                .then()
                                .assertThat()
                                .statusCode(204);
        }

}
