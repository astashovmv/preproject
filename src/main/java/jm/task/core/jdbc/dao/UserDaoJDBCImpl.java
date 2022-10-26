package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public  void createUsersTable() {
        PreparedStatement preparedStatement = null;
        User user = new User("Maxim", "Astashov", (byte)26);
        //user.setId(1L);
        //String sql = "insert into users (id, name, lastName, age) values(?, ?, ?, ?)";
        String sql = "insert into users (name, lastName, age) values(?, ?, ?)";
        Util util = new Util();
        try {
            preparedStatement = util.getConnection().prepareStatement(sql);
            //preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
        dropUsersTable();
    }

    public void dropUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = "delete from users";
        Util util = new Util();
        try {
            preparedStatement = util.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        PreparedStatement preparedStatement = null;
        String sql = "insert into users (name, lastName, age) values(?, ?, ?)";
        Util util = new Util();
        try {
            preparedStatement = util.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        Util util = new Util();
        User user = new User("Maxim", "Astashov", (byte)26);
        user.setId(1L);
        String sql = "delete from users where id = ?";

        try {
            preparedStatement = util.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "select id, name, lastName, age from users";

        try {
            Util util = new Util();
            Statement statement = null;

            statement = util.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
        return userList;
    }

    public void cleanUsersTable() {
        List<User> userListDelete = getAllUsers();
        userListDelete.clear();
        dropUsersTable();
        }
    }

