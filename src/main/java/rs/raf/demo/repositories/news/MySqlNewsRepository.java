package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.Comment;
import rs.raf.demo.entities.News;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Override
    public List<News> allNews() {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetAuthor = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news");

            //NEWS
            while (resultSet.next()) {
                //get news
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date createdAt = resultSet.getDate("createdAt");
                int visits_num = resultSet.getInt("visits_num");

                News news = new News(id, title, content, createdAt);
                news.setVisits_num(visits_num);

                //get author from news
                preparedStatement = connection.prepareStatement("select * from user where email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetAuthor = preparedStatement.executeQuery();

                //AUTHOR
                while (resultSetAuthor.next()) {
                    String email = resultSetAuthor.getString("email");
                    String name = resultSetAuthor.getString("name");
                    String surname = resultSetAuthor.getString("surname");
                    int role = resultSetAuthor.getInt("role");
                    int status = resultSetAuthor.getInt("status");
                    String password = resultSetAuthor.getString("password");

                    User author = new User(email, name, surname, role, status, password);

                    synchronized (this) {
                        news.setAuthor(author);
                    }
                }

                //get categoryName from news
                preparedStatement = connection.prepareStatement("select * from category where name = ?");
                preparedStatement.setString(1, resultSet.getString("category_name"));
                resultSetCategory = preparedStatement.executeQuery();

                //CATEGORY
                while (resultSetCategory.next()) {
                    String name = resultSetCategory.getString("name");
                    String description = resultSetCategory.getString("description");

                    Category category = new Category(name, description);

                    synchronized (this) {
                        news.setCategory(category);
                    }
                }

                newsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News findNews(Integer id) {
        return null;
    }
}
