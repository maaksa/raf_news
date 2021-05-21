package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {

    private Integer id;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Date createdAt;

    private News news;

    public Comment() {
    }

    public Comment(Integer id, String author, String content, Date createdAt, News news) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.news = news;
    }
}





