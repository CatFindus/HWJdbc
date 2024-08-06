package mapper.dao;

import lombok.SneakyThrows;
import model.Message;

import java.sql.ResultSet;

public class MessageMapper {

    @SneakyThrows
    public static Message map(ResultSet set) {
        return Message
                .builder()
                .id(set.getInt("id"))
                .chatId(set.getInt("chat_id"))
                .userId(set.getInt("user_id"))
                .message(set.getString("message"))
                .build();
    }
}
