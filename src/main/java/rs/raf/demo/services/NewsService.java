package rs.raf.demo.services;

import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;


    public List<News> allNewNews(int pageNum) {
        return this.newsRepository.allNewNews(pageNum);
    }

    public List<News> allTopVisitedNews() {
        return this.newsRepository.allTopVisitedNews();
    }

    public List<News> allNewsByCategory(String category) {
        return this.newsRepository.allNewsByCategory(category);
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

}
