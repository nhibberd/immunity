package immunity.data.blog;

public class Blog {
    public String title;
    public String content;
    public String type;
    public String link;
    public Long timestamp;

    public Blog(String title, String content, String type, String link, Long timestamp) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.link = link;
        this.timestamp = timestamp;
    }

}
