package rs.raf.demo.services;

import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;


    public List<News> allNews() {
        return this.newsRepository.allNews();
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

}
