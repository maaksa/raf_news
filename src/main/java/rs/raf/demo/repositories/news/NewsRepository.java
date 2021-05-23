package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.entities.News;

import java.util.List;

public interface NewsRepository {

    public List<News> allNewNews(int pageNum);

    public List<News> allTopVisitedNews();

    public List<News> allNewsByCategory(String category);

    public List<News> allNewsByTag(String tag);

    public News findNews(Integer id);

    public Comment addComment(Comment comment, Integer id);

    public News addNews(News news);

    public void deleteNews(Integer id);

}
