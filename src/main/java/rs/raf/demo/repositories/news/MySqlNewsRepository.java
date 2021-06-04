package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.*;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Override
    public List<News> allNewNews(int pageNum) {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetAuthor = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;

        try {
            int currPage = (pageNum - 1) * 4;
            connection = this.newConnection();

            // statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("select * from news order by createdAt desc limit ?,4");
            preparedStatement.setInt(1, currPage);
            resultSet = preparedStatement.executeQuery();
            //resultSet = statement.executeQuery("select * from news order by createdAt desc limit ?,2"); //prvi kec je koliko ce da preskoci, drugi kec koliko ce da prikaze posle preskakanja


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
            //this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSetAuthor);
            this.closeResultSet(resultSetCategory);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allTopVisitedNews() {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetAuthor = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;

        try {
            int currPage = 1;
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news where createdAt between NOW() - interval 30 day and NOW() order by visits_num desc");

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
            this.closeResultSet(resultSetAuthor);
            this.closeResultSet(resultSetCategory);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allNewsByCategory(String category) {
        List<News> newsList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetAuthor = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            //statement = connection.createStatement();
            //resultSet = statement.executeQuery("select * from news where category_name like ? order by createdAt desc");
            preparedStatement = connection.prepareStatement("select * from news where category_name like ? order by createdAt desc");
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();

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

                    Category categoryToSave = new Category(name, description);

                    synchronized (this) {
                        news.setCategory(categoryToSave);
                    }
                }

                newsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeResultSet(resultSetAuthor);
            this.closeResultSet(resultSetCategory);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allNewsByTag(String tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetCategory = null;
        ResultSet resultSetTags = null;
        ResultSet resultSetNews2 = null;

        List<News> newsList = new ArrayList<>();

        try {
            connection = this.newConnection();

            //get tag by name
            preparedStatement = connection.prepareStatement("SELECT * FROM tag where word = ?");
            preparedStatement.setString(1, tag);
            resultSet = preparedStatement.executeQuery();

            //TAG
            if (resultSet.next()) {
                int idTag = resultSet.getInt("id");

                //get news id related to tag
                preparedStatement = connection.prepareStatement("select id_news from tag_news where id_tag = ?");
                preparedStatement.setInt(1, idTag);
                resultSetNews = preparedStatement.executeQuery();

                //NEWS
                while (resultSetNews.next()) {
                    int newsId = resultSetNews.getInt("id_news");

                    preparedStatement = connection.prepareStatement("select * from news where id = ?");
                    preparedStatement.setInt(1, newsId);
                    resultSetNews2 = preparedStatement.executeQuery();

                    while (resultSetNews2.next()) {
                        int id = resultSetNews2.getInt("id");
                        String title = resultSetNews2.getString("title");
                        String content = resultSetNews2.getString("content");
                        Date createdAt = resultSetNews2.getDate("createdAt");
                        int visits_num = resultSetNews2.getInt("visits_num");

                        News news = new News(id, title, content, createdAt);
                        news.setVisits_num(visits_num);

                        //get category for news
                        preparedStatement = connection.prepareStatement("select * from category where name = ?");
                        preparedStatement.setString(1, resultSetNews2.getString("category_name"));
                        resultSetCategory = preparedStatement.executeQuery();

                        //CATEGORY
                        while (resultSetCategory.next()) {
                            String name = resultSetCategory.getString("name");
                            String description = resultSetCategory.getString("description");

                            Category categoryToSave = new Category(name, description);

                            synchronized (this) {
                                news.setCategory(categoryToSave);
                                newsList.add(news);
                            }
                        }
                    }
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSetCategory);
            this.closeResultSet(resultSetNews);
            this.closeResultSet(resultSetNews2);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News findNews(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetComments = null;
        ResultSet resultSetTags = null;
        ResultSet resultSetAuthor = null;

        News news = null;

        try {
            connection = this.newConnection();

            //get news by id
            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            //NEWS
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date createdAt = resultSet.getDate("createdAt");
                int currVisits = resultSet.getInt("visits_num");

                news = new News(id, title, content, createdAt);

                currVisits += 1;

                //todo visits number
                news.setVisits_num(currVisits);

                System.out.println(currVisits);

                preparedStatement = connection.prepareStatement("update news set visits_num = ? where id = ?");
                preparedStatement.setInt(1, currVisits);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                //get comments related to news
                preparedStatement = connection.prepareStatement("select * from comment where news = ? order by createdAt desc");
                preparedStatement.setInt(1, resultSet.getInt("id"));
                resultSetComments = preparedStatement.executeQuery();

                //COMMENT
                while (resultSetComments.next()) {
                    int id1 = resultSetComments.getInt("id");
                    String author1 = resultSetComments.getString("author");
                    String content1 = resultSetComments.getString("content");
                    Date createdAt1 = resultSetComments.getDate("createdAt");

                    Comment comment = new Comment(id1, author1, content1, createdAt1);

                    synchronized (this) {
                        news.getComments().add(comment);
                    }
                }

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

                //get tags related to news
                preparedStatement = connection.prepareStatement("select id_tag from tag_news where id_news = ?");
                preparedStatement.setInt(1, newsId);
                resultSetTags = preparedStatement.executeQuery();

                while (resultSetTags.next()) {
                    int tagId = resultSetTags.getInt("id_tag");

                    //get tag
                    preparedStatement = connection.prepareStatement("SELECT * FROM tag where id = ?");
                    preparedStatement.setInt(1, tagId);
                    resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        int id1 = resultSet.getInt("id");
                        String word = resultSet.getString("word");

                        Tag tag = new Tag(id1, word);
                        synchronized (this) {
                            news.getTags().add(tag);
                        }
                    }
                }
            }

            resultSetComments.close();
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSetTags);
            this.closeResultSet(resultSetComments);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
            this.closeResultSet(resultSetAuthor);
        }

        return news;
    }

    @Override
    public Comment addComment(Comment comment, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (comment.getAuthor() == null || comment.getContent() == null) {
            return null;
        }

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comment (author, content, createdAt, news) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor());
            preparedStatement.setString(2, comment.getContent());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public News addNews(News news) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> tagIds = new ArrayList<>();
        int newsId = 0;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            //dodajemo nove tagove
            for (Tag tag : news.getTags()) {
                preparedStatement = connection.prepareStatement("select * from tag where word = ? ", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, tag.getWord());
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO tag (word) VALUES (?)", generatedColumns);
                    preparedStatement.setString(1, tag.getWord());

                    preparedStatement.executeUpdate();
                    resultSet = preparedStatement.getGeneratedKeys();

                    //cuvamo id novog taga
                    if (resultSet.next()) {
                        tagIds.add(resultSet.getInt(1));
                    }
                    //cuvamo id postojeceg taga
                } else {
                    tagIds.add(resultSet.getInt(1));
                }
            }

            preparedStatement = connection.prepareStatement("INSERT INTO news (title, content, createdAt, category_name, author) VALUES(?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(4, news.getCategory().getName());
            preparedStatement.setString(5, news.getAuthor().getEmail());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                //cuvamo id novog news-a
                newsId = resultSet.getInt(1);
                news.setId(newsId);
            }

            //many-many table
            for (int idTag : tagIds) {
                preparedStatement = connection.prepareStatement("INSERT INTO tag_news (id_news, id_tag) VALUES(?, ?)");
                preparedStatement.setInt(1, newsId);
                preparedStatement.setInt(2, idTag);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public void deleteNews(Integer id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public News updateNews(News news, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> tagIds = new ArrayList<>();
        int newsId = 0;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            //dodajemo nove tagove
            for (Tag tag : news.getTags()) {
                preparedStatement = connection.prepareStatement("select * from tag where word = ? ", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, tag.getWord());
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO tag (word) VALUES (?)", generatedColumns);
                    preparedStatement.setString(1, tag.getWord());

                    preparedStatement.executeUpdate();
                    resultSet = preparedStatement.getGeneratedKeys();

                    //cuvamo id novog taga
                    if (resultSet.next()) {
                        tagIds.add(resultSet.getInt(1));
                    }
                    //cuvamo id postojeceg taga
                } else {
                    tagIds.add(resultSet.getInt(1));
                }
            }

            preparedStatement = connection.prepareStatement("update news set title = ?, content = ?, createdAt = ?, category_name = ?, author = ? where id = ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(4, news.getCategory().getName());
            preparedStatement.setString(5, news.getAuthor().getEmail());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();

            //todo updae tagove
//            for (int idTag : tagIds) {
//                preparedStatement = connection.prepareStatement("update tag_news set id_news = ?, id_tag = ? where id_news = ?");
//                preparedStatement.setInt(1, id);
//                preparedStatement.setInt(2, idTag);
//                preparedStatement.setInt(3, id);
//            }

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

        return news;
    }

}
