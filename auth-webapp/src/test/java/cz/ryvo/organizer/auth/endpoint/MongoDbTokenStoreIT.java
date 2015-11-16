package cz.ryvo.organizer.auth.endpoint;

import cz.ryvo.organizer.auth.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.oauth2.common.util.OAuth2Utils.GRANT_TYPE;

public class MongoDbTokenStoreIT extends IntegrationTest {

    private String token;

    @Before
    public void getToken() {
        String accessToken = given()
                .accept(JSON)
                .formParam(GRANT_TYPE, "client_credentials")
                .auth().preemptive().basic("test-client", "test-client")
                .log().all()

                .when()
                .post("/oauth/token")

                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");

        assertThat(accessToken).isNotNull().isNotEmpty();

        token = accessToken;
    }

    @Test
    public void test() {
       System.out.println(token);
    }
}
