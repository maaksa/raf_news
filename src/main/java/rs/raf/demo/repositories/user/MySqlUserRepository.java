package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public List<User> allUser() {

        List<User> userList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user");

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int role = resultSet.getInt("role");
                int status = resultSet.getInt("status");
                String password = resultSet.getString("password");

                User user = new User(email, name, surname, role, status, password);

                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return userList;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from user where email = ? ");
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("INSERT INTO user (email, name, surname, role, password) VALUES (?,?,?,?,?)");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getSurname());
                preparedStatement.setInt(4, user.getRole());
                preparedStatement.setString(5, user.getHashedPassword());

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();

            } else
                user = null;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User updateUser(User user, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("update user set email = ?, name = ?, surname = ?, role = ?, password = ? where email = ?");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setInt(4, user.getRole());
            preparedStatement.setString(5, user.getHashedPassword());
            preparedStatement.setString(6, email);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void changeStatus(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int status = resultSet.getInt("status");

                if (status == 1) {
                    status = 0;
                    preparedStatement = connection.prepareStatement("update user set status = ? where email = ?");
                    preparedStatement.setInt(1, status);
                    preparedStatement.setString(2, email);

                    preparedStatement.executeUpdate();
                } else {
                    status = 1;
                    preparedStatement = connection.prepareStatement("update user set status = ? where email = ?");
                    preparedStatement.setInt(1, status);
                    preparedStatement.setString(2, email);

                    preparedStatement.executeUpdate();
                }
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }


}
