package rs.raf.demo.resources;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.entities.News;
import rs.raf.demo.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @GET
    @Path("/page-num/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewNews(@PathParam("pageNum") int pageNum) {
        return this.newsService.allNewNews(pageNum);
    }

    @GET
    @Path("/top-visited")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allTopVisitedNews() {
        return this.newsService.allTopVisitedNews();
    }

    @GET
    @Path("/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByCategory(@PathParam("category") String category) {
        return this.newsService.allNewsByCategory(category);
    }

    @GET
    @Path("/tag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByTag(@PathParam("tag") String tag) {
        return this.newsService.allNewsByTag(tag);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.findNews(id);
    }

    @POST
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment createComment(@Valid Comment comment, @PathParam("id") Integer id) {
        return this.newsService.addComment(comment, id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News createNews(@Valid News news) {
        return this.newsService.addNews(news);
    }

}
