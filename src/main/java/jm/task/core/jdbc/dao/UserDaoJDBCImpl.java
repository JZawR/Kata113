package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE User ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45), "
                    + "lastName VARCHAR(45), "
                    + "age TINYINT, "
                    + "PRIMARY KEY(id));");
            connection.commit();
            System.out.println("Таблица User успешно создана");
        } catch (SQLException e) {
            System.out.println("Такая таблица уже существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE kata113.user;");
            connection.commit();
            System.out.println("Таблица User удалена");
        } catch (SQLException e) {
            System.out.println("Такая таблица не существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (NAME, LASTNAME," +
                " AGE) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить пользователя");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE ID=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя по id");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
//            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
//            if (resultSet.absolute(2)) {
//                System.out.println("Значение на второй 2 сроке 2 столбца: " + resultSet.getObject(2));
//            }
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
            connection.commit();
            System.out.println("Список из " + userList.size() + " пользователя(ей) получен");
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить список всех пользователей");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE USER");
            connection.commit();
            System.out.println("Таблица User очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
