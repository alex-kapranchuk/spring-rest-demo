package model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Book {

    private String userId;
    private List<ISBN> collectionOfIsbns;

    @Data
    @Builder
    public static class ISBN {
        private String isbn;
    }
}
