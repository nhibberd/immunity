package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.core.*;

import java.sql.Connection;
import static immunity.tool.Generators.generatorAge;

public class AddBlog implements Action2<Connection,Blog> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Blog data) {
        data.timestamp = generatorAge();
        database.updateObjects(connection,"INSERT INTO \"blog\"( title, content, type, link, timestamp) VALUES (?, ?, ?, ?, ?)",
                data.title,data.content, data.type, data.link, data.timestamp);
    }
}
