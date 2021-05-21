package rs.raf.demo.resources;

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
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all() {
        return this.newsService.allNews();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.findNews(id);
    }

}
