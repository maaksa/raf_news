package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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


}
