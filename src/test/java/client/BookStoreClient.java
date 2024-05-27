package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Book;
import model.User;

public class BookStoreClient {

    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    public Response getBooks() {
        return RestAssured.get(BASE_URL + "/BookStore/v1/Books");
    }

    public Response createUser(User user) {
        return RestAssured.given()
                .contentType("application/json")
                .header("accept", "application/json")
                .header("authorization", "Basic MTox")
                .body(user)
                .post(BASE_URL + "/Account/v1/User");
    }

    public Response createBooks(Book book) {
        return RestAssured.given()
                .contentType("application/json")
                .body(book)
                .post(BASE_URL + "/BookStore/v1/Books");
    }

    public Response createBooksAuthorization(Book book, String token) {
        return RestAssured.given()
                .contentType("application/json")
                .header("authorization", "Bearer " + token)
                .body(book)
                .post(BASE_URL + "/BookStore/v1/Books");
    }

    public Response authorizedUser(User user) {
        return RestAssured.given()
                .contentType("application/json")
                .header("accept", "application/json")
                .body(user)
                .post(BASE_URL + "/Account/v1/Authorized");

    }

    public Response generateToken(User user) {
        return RestAssured.given()
                .contentType("application/json")
                .header("accept", "application/json")
                .body(user)
                .post(BASE_URL + "/Account/v1/GenerateToken");
    }
}
