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
import com.pik.contact.api.external.helpers.GraphQLQuery;

import net.minidev.json.JSONObject;

import com.jayway.restassured.response.Header;

import static com.jayway.restassured.RestAssured.*;

@DisplayName("API calls to gorest.co.in with GraphQL")
public class GraphQLAPITest {

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

                GraphQLQuery createUserMutation = new GraphQLQuery();

                createUserMutation.setQuery(
                                "mutation ($name: String!, $gender: String!, $email: String!, $status: String!) { createUser( input: { name: $name, gender: $gender, email: $email, status: $status } ) { user { id name gender email status }} }");

                JSONObject createUserVars = new JSONObject();
                createUserVars.put("name", "John Pourdanis");
                createUserVars.put("email", randomEmail);
                createUserVars.put("gender", "male");
                createUserVars.put("status", "active");

                createUserMutation.setVariables(createUserVars.toString());

                request.body(createUserMutation);

                newUserId = request.post("/graphql")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .and()
                                .extract()
                                .path("data.createUser.user.id");
                System.out.println("The new user id is: " + newUserId);
        }

        @AfterClass
        @DisplayName("Should delete the user.")
        public static void teardown() {

                GraphQLQuery deleteUserMutation = new GraphQLQuery();

                deleteUserMutation.setQuery(
                                "mutation ($id: Int!) { deleteUser(input: { id: $id }) { user { id name gender email  status  } } }");

                JSONObject deleteUserVars = new JSONObject();
                deleteUserVars.put("id", newUserId);

                deleteUserMutation.setVariables(deleteUserVars.toString());

                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(deleteUserMutation)
                                .when()
                                .post("/graphql")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("data.deleteUser.user.id", Matchers.equalTo(newUserId));
        }

        @Test
        @DisplayName("Should get all users")
        public void test1() {
                GraphQLQuery getAllUsersQuery = new GraphQLQuery();

                getAllUsersQuery.setQuery(
                                "{ users {nodes { id name email gender status } } }");

                given()
                                .header(new Header("Authorization", "Bearer " + bearerToken))
                                .header(new Header("Content-type", "application/json"))
                                .body(getAllUsersQuery)
                                .when()
                                .post("/graphql")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("data.users.nodes.size()", Matchers.greaterThan(0));

        }

        @Test
        @DisplayName("Should get user details by id")
        public void test2() {
                GraphQLQuery getUserDetailsQuery = new GraphQLQuery();

                getUserDetailsQuery.setQuery(
                                "query ($id: ID!) { user(id: $id) { id name email gender status } }");

                JSONObject getUserDetailsVars = new JSONObject();
                getUserDetailsVars.put("id", newUserId);
                getUserDetailsQuery.setVariables(getUserDetailsVars.toString());

                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(getUserDetailsQuery)
                                .when()
                                .post("/graphql")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("data.user.name", Matchers.equalTo("John Pourdanis"))
                                .body("data.user.email", Matchers.equalTo(randomEmail));
        }

        @Test
        @DisplayName("Should update the name of user")
        public void test3() {
                String nameToUpdate = "John Pourdanopoulos";

                GraphQLQuery updateUserMutation = new GraphQLQuery();

                updateUserMutation.setQuery(
                                "mutation ($id: Int!, $name: String!) { updateUser ( input: { id: $id, name: $name }) { user { id name gender email status  } } }");

                JSONObject updateUserVars = new JSONObject();
                updateUserVars.put("name", nameToUpdate);
                updateUserVars.put("id", newUserId);
                updateUserMutation.setVariables(updateUserVars.toString());

                given()
                                .headers(
                                                "Authorization",
                                                "Bearer " + bearerToken,
                                                "Content-Type",
                                                ContentType.JSON)
                                .body(updateUserMutation)
                                .when()
                                .post("/graphql")
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .body("data.updateUser.user.name", Matchers.equalTo(nameToUpdate));
        }
}
