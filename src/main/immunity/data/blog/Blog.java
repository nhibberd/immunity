package immunity.data.blog;

import toro.EdgeResultSet;
import toro.FromDb;

public class Blog implements FromDb<Blog> {
    public String title;
    public String content;
    public String type;
    public String link;
    public Long timestamp;
    public Integer id;
    public Integer authorid;

    public Blog(String title, String content, String type, String link, Long timestamp, Integer id, Integer authorid) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.link = link;
        this.timestamp = timestamp;
        this.id = id;
        this.authorid = authorid;
    }

    public Blog() {
    }

    public Blog from(EdgeResultSet resultSet) {
        return new Blog(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)
                ,resultSet.getLong(5),resultSet.getInt(6),resultSet.getInt(7));

    }
}
