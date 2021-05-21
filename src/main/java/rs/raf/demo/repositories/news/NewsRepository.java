package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;

import java.util.List;

public interface NewsRepository {

    public List<News> allNewNews(int pageNum);

    public List<News> allTopVisitedNews();

    public List<News> allNewsByCategory(String category);

    public News findNews(Integer id);

}
