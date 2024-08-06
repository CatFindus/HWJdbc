package dao;

import mapper.dao.UserMapper;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private final String byId =  "SELECT * FROM aston.users WHERE id=?;";
    private final String all = "SELECT * FROM aston.users;";
    private final String create = "INSERT into aston.users(user_name)  VALUES (?);";
    private final String update = "UPDATE aston.users SET user_name = ? WHERE id = ?;";
    private final String delete = "DELETE FROM aston.users WHERE id = ?;";

    public User getById(int id) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(byId);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())return UserMapper.map(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll() {
        try(Connection connection = Fabric.get()) {
            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(all);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(UserMapper.map(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User create(User user) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setString(1, user.getUserName());
            int rows = statement.executeUpdate();
            if (rows==0) throw new IllegalArgumentException();
            try(ResultSet generated = statement.getGeneratedKeys()) {
                if (generated.next()) user.setId(generated.getInt(1));
                else throw new IllegalArgumentException();
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User update(User user, int id) {
        try(Connection connection = Fabric.get()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, user.getUserName());
            statement.setInt(2, id);
            int rows = statement.executeUpdate();
            if (rows == 0) throw  new IllegalArgumentException();
            else {
                user.setId(id);
                return user;
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
