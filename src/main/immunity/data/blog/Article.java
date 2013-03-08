package immunity.data.blog;

import java.util.List;

public class Article {
    public Blog blog;
    public List<Comments> comments;

    public Article(Blog blog, List<Comments> comments) {
        this.blog = blog;
        this.comments = comments;
    }
}
