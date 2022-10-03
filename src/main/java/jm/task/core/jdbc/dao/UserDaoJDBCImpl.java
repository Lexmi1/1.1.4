package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = Util.getInstance();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users" +
                "(ID serial primary key, " +
                " NAME VARCHAR(50) not NULL, " +
                " LASTNAME VARCHAR (50) not NULL, " +
                " AGE INT not NULL)";
        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (Connection connection = util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM users WHERE ID = ?";

        try (Connection connection = util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(Long.valueOf(resultSet.getInt("ID")));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE users";

        try (Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
