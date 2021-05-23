package rs.raf.demo.services;

import rs.raf.demo.entities.Comment;
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

    public List<News> allNewsByTag(String tag) {
        return this.newsRepository.allNewsByTag(tag);
    }


    public List<News> allNewsByCategory(String category) {
        return this.newsRepository.allNewsByCategory(category);
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

    public Comment addComment(Comment comment, Integer id) {
        return this.newsRepository.addComment(comment, id);
    }

    public News addNews(News news) {
        return this.newsRepository.addNews(news);
    }

    public void deleteNews(Integer id){
        this.newsRepository.deleteNews(id);
    }

}
