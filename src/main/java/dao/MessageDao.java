package dao;

import mapper.dao.MessageMapper;
import model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private final String byId = "SELECT * FROM aston.messages WHERE id=?;";
    private final String all = "SELECT * FROM aston.messages;";
    private final String create = "INSERT into aston.messages(user_id, chat_id, message)  VALUES (?,?,?);";
    private final String update = "UPDATE aston.messages SET message = ? WHERE id = ?;";
    private final String delete = "DELETE FROM aston.messages WHERE id = ?;";

    public Message getById(int id) {
        try (Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(byId);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return MessageMapper.map(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getAll() {
        try (Connection connection = Fabric.get()) {
            List<Message> messages = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(all);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(MessageMapper.map(resultSet));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message create(Message message) {
        try (Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setInt(1, message.getUserId());
            statement.setInt(2, message.getChatId());
            statement.setString(3, message.getMessage());
            int rows = statement.executeUpdate();
            if (rows == 0) throw new IllegalArgumentException();
            try (ResultSet generated = statement.getGeneratedKeys()) {
                if (generated.next()) message.setId(generated.getInt(1));
                else throw new IllegalArgumentException();
            }
            return message;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message update(Message message, int id) {
        try (Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, message.getMessage());
            statement.setInt(2, id);
            int rows = statement.executeUpdate();
            if (rows == 0) throw new IllegalArgumentException();
            else {
                message.setId(id);
                return message;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows == 0) throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
