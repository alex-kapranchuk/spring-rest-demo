package test;

import builder.BookBuilder;
import builder.UserBuilder;
import client.BookStoreClient;
import io.restassured.response.Response;
import model.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookStoreTests {

    private BookStoreClient bookStoreClient;

    @BeforeClass
    public void setUp() {
        bookStoreClient = new BookStoreClient();
    }

    @Test
    public void testGetBooks() {
        Response response = bookStoreClient.getBooks();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testCreateBookNotAuthorized() {
        String userId = bookStoreClient.createUser(new UserBuilder().createTestuser())
                .as(UserResponse.class)
                .getUserID();

        String isbn = bookStoreClient.getBooks()
                .as(BookResponse.BookList.class)
                .getBooks()
                .get(0)
                .getIsbn();


        Book book = BookBuilder.createTestBook(userId, isbn);

        Response response = bookStoreClient.createBooks(book);
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    @Test
    public void testCreateBookAuthorized() {
        User user = new UserBuilder().createTestuser();
        String userId = bookStoreClient.createUser(user)
                .as(UserResponse.class)
                .getUserID();

        String token = bookStoreClient.generateToken(user)
                .as(TokenResponse.class)
                .getToken();

        String isbn = bookStoreClient.getBooks()
                .as(BookResponse.BookList.class)
                .getBooks()
                .get(0)
                .getIsbn();

        Book book = BookBuilder.createTestBook(userId, isbn);

        Response response = bookStoreClient.createBooksAuthorization(book, token);
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
