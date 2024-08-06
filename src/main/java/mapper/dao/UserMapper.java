package mapper.dao;

import lombok.SneakyThrows;
import model.User;

import java.sql.ResultSet;

public class UserMapper {
    @SneakyThrows
    public static User map(ResultSet set) {
        return User
                .builder()
                .id(set.getInt("id"))
                .userName(set.getString("user_name"))
                .build();

    }

}
