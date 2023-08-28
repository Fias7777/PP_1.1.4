package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `customer`.`users` (\n" +
                "  `id` INT NOT NULL auto_increment primary key,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE customer.users";
        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO customer.users(name, lastName, age) values (?, ?, ?)";

        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM customer.users where id = ?;";

        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM customer.users";

        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

        ) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE customer.users;";
        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
