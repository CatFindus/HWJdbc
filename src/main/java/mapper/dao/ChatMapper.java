package mapper.dao;

import lombok.SneakyThrows;
import model.Chat;

import java.sql.ResultSet;

public class ChatMapper {

    @SneakyThrows
    public static Chat map(ResultSet set) {
        return Chat
                .builder()
                .id(set.getInt("id"))
                .name(set.getString("name"))
                .build();
    }
}
