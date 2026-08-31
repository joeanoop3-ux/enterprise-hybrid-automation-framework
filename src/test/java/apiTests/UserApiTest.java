package apiTests;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest {

    private final String BASE_URL = "https://reqres.in";

    @Test(priority = 1, description = "Validate GET endpoint fetches user profile correctly")
    public void testGetUserProfile() {
        given()
            .baseUri(BASE_URL)
            .header("Content-Type", "application/json")
        .when()
            .get("/users/2")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .time(lessThan(2000L)) // Verifies performance response SLA is under 2 seconds
            .body("data.id", equalTo(2))
            .body("data.first_name", equalTo("Janet"))
            .body("data.email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$"));
    }

    @Test(priority = 2, description = "Validate POST endpoint successfully provisions a new user record")
    public void testCreateNewUserRecord() {
        // Encapsulating the raw JSON transaction payload
        String jsonPayload = "{\n" +
                "    \"name\": \"Joe Anoop\",\n" +
                "    \"job\": \"Lead SDET\"\n" +
                "}";

        given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(jsonPayload)
        .when()
            .post("/users")
        .then()
            .statusCode(201) // 201 Created validation
            .body("name", equalTo("Joe Anoop"))
            .body("job", equalTo("Lead SDET"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue());
    }
}
