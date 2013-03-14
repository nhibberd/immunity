package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.core.*;

import java.sql.Connection;
import static immunity.tool.Generators.generatorAge;

public class AddBlog implements Action2<Connection,Blog> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Blog data) {
        data.timestamp = generatorAge();
        String linkid = data.link.substring(data.link.lastIndexOf('/') + 1);
        database.updateObjects(connection,"INSERT INTO \"blog\"( title, content, type, link, timestamp, authorid, image) VALUES (?, ?, ?, ?, ?, ?, ?)",
                data.title,data.content, data.type, linkid, data.timestamp, 0, data.image);
    }
}
