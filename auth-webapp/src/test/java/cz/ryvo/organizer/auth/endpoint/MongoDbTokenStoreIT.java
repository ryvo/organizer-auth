package cz.ryvo.organizer.auth.endpoint;

import cz.ryvo.organizer.auth.IntegrationTest;
import cz.ryvo.organizer.testutils.conditions.Conditions;
import cz.ryvo.organizer.testutils.matchers.UUIDMatcher;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.GRANT_TYPE;

public class MongoDbTokenStoreIT extends IntegrationTest {

    private String token;

    @Before
    public void getToken() {
        String accessToken = given()
                .accept(JSON)
                .formParam(GRANT_TYPE, "client_credentials")
                .auth().preemptive().basic("test_client_with_client_credentials", "test_client_with_client_credentials")
                .log().all()

                .when()
                .post("/oauth/token")

                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");

        assertThat(accessToken).isNotNull().isNotEmpty().is(Conditions.matcher(UUIDMatcher.matchesUUID()));

        token = accessToken;
    }

    @Test
    public void checkTokenTest_OK() {
       given()
               .accept(JSON)
               .formParam("token", token)
               .authentication().preemptive().basic("test_client_with_client_credentials", "test_client_with_client_credentials")
               .log().all()

               .when()
               .post("/oauth/check_token")

               .then()
               .log().all()
               .statusCode(200)
               .body("scope", contains("test-client_credentials-scope"))
               .body("exp", greaterThan(0))
               .body("authorities", contains("ROLE_CHECK_TOKEN"))
               .body("client_id", equalTo("client_credentials"));
    }
}
