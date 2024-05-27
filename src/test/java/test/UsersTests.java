package test;

import builder.UserBuilder;
import client.BookStoreClient;
import io.restassured.response.Response;
import model.TokenResponse;
import model.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UsersTests {
    private BookStoreClient bookStoreClient;

    @BeforeClass
    public void setUp() {
        bookStoreClient = new BookStoreClient();
    }

    @Test
    public void testCreateUser() {
        User user = new UserBuilder().createTestuser();

        Response response = bookStoreClient.createUser(user);
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void testAuthorizedUser() {
        User user = new UserBuilder().createTestuser();

        Response response = bookStoreClient.createUser(user)
                .then()
                .statusCode(201)
                .extract()
                .response();

        response = bookStoreClient.authorizedUser(user);
        Assert.assertEquals(response.getStatusCode(), 200);

        boolean tokenStatus = response.as(Boolean.class);
        Assert.assertFalse(tokenStatus, "Status should be true");
    }

    @Test
    public void testGenerateToken() {
        User user = new UserBuilder().createTestuser();

        bookStoreClient.createUser(user)
                .then()
                .statusCode(201);

        TokenResponse tokenResponse = bookStoreClient.generateToken(user)
                .as(TokenResponse.class);

        Assert.assertNotNull(tokenResponse.getToken(), "Token should not be null");
        Assert.assertEquals(tokenResponse.getStatus(), "Success", "Status should be 'Success'");

        Response response = bookStoreClient.authorizedUser(user)
                .then()
                .statusCode(200)
                .extract()
                .response();

        boolean tokenStatus = response.as(Boolean.class);
        Assert.assertTrue(tokenStatus, "Status should be true");
    }
}
