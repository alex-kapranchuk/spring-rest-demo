package builder;

import model.Book;

import java.util.Collections;

public class BookBuilder {

    public static Book createTestBook(String userId, String isbn) {
        return Book.builder()
                .userId(userId)
                .collectionOfIsbns(Collections.singletonList(
                        Book.ISBN.builder()
                                .isbn(isbn)
                                .build()
                ))
                .build();
    }
}
