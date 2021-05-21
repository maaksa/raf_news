package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class News {

    private Integer id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Date createdAt;

    private int visits_num;

    private User author;

    private Category category;

    public News() {
    }

    public News(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public News(Integer id, String title, String content) {
        this(title, content);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
