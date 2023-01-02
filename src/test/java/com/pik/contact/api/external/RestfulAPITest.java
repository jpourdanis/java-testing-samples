package com.pik.contact.api.external;

import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.response.Header;

import static com.jayway.restassured.RestAssured.*;

@DisplayName("API calls to gorest.co.in with REST")
public class RestfulAPITest {

        public static Integer newUserId;

        public static String randomEmail = RandomStringUtils.randomAlphabetic(8) + "@mail.com";

        public static String bearerToken = System.getProperty("APITOKEN");

        @BeforeClass
        @DisplayName("Should create a user.")
        public static void setup() {
                RestAssured.baseURI = "https://gorest.co.in/public/v2";

                RequestSpecification request = RestAssured.given();

                request.header(new Header("Authorization", "Bearer " + bearerToken));
                request.header(new Header("Content-type", "application/json"));

                request.queryParam("name", "John Pourdanis");
                request.queryParam("email", randomEmail);
                request.queryParam("gender", "male");
                request.queryParam("status", "active");

                newUserId = request.post("/users")
                                .then()
                                .assertThat()
                                .statusCode(201)
                                .extract()
                                .path("id");
                System.out.println("The new user id is: " + newUserId);
        }

        @Test
        @DisplayName("Should get all users")
        public void testA() {
                given()
                                .header(new Header("Authorization", "Bearer " + bearerToken))
                                .header(new Header("Content-type", "application/json"))
                                .when()
                                .get("/users")
                                .then()
                                .assertThat()
                                .statusCode(200);
        }

        @Test
        @DisplayName("Should get user details by id")
        public void testB() {
                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newUserId)
                                .when()
                                .get("/users/{id}")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("name", Matchers.equalTo("John Pourdanis"))
                                .body("email", Matchers.equalTo(randomEmail));
        }

        @Test
        @DisplayName("Should update the name of user")
        public void testC() {
                String updatedName = "John Pourdanopoulos";
                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newUserId)
                                .queryParam("name", updatedName)
                                .when()
                                .put("/users/{id}")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("name", Matchers.equalTo(updatedName))
                                .body("email", Matchers.equalTo(randomEmail));
        }

        @AfterClass
        @DisplayName("Should delete the user.")
        public static void teardown() {
                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .pathParam("id", newUserId)
                                .when()
                                .delete("/users/{id}")
                                .then()
                                .assertThat()
                                .statusCode(204);
        }
}
