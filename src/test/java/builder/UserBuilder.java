package builder;

import com.github.javafaker.Faker;
import model.User;

public class UserBuilder {

    Faker faker = new Faker();

    String userName = faker.name().username();
    String userPassword = faker.internet().password(8, 16) + "*A";

    public User createTestuser() {
        return User.builder()
                .userName(userName)
                .password(userPassword)
                .build();
    }
}
