package dao;

import mapper.dao.ChatMapper;
import model.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDao {
    private final String byId =  "SELECT * FROM aston.chats WHERE id=?;";
    private final String all = "SELECT * FROM aston.chats;";
    private final String create = "INSERT into aston.chats(name) VALUES (?);";
    private final String update = "UPDATE aston.chats SET name = ? WHERE id = ?;";
    private final String delete = "DELETE FROM aston.chats WHERE id = ?;";

    public Chat getById(int id) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(byId);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())return ChatMapper.map(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Chat> getAll() {
        try(Connection connection = Fabric.get()) {
            List<Chat> chats = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(all);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                chats.add(ChatMapper.map(resultSet));
            }
            return chats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Chat create(Chat chat) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setString(1, chat.getName());
            int rows = statement.executeUpdate();
            if (rows==0) throw new IllegalArgumentException();
            try(ResultSet generated = statement.getGeneratedKeys()) {
                if (generated.next()) chat.setId(generated.getInt(1));
                else throw new IllegalArgumentException();
            }
            return chat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Chat update(Chat chat, int id) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, chat.getName());
            statement.setInt(2, id);
            int rows = statement.executeUpdate();
            if (rows == 0) throw  new IllegalArgumentException();
            else {
                chat.setId(id);
                return chat;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows == 0) throw  new IllegalArgumentException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
