package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.core.Function2;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction;
import immunity.data.core.Status;
import java.sql.Connection;
import static immunity.tool.Generators.generatorAge;

public class AddBlog implements Function2<Connection, Blog, Status> {
    private final immunity.db.Db Db = new immunity.db.Db();

    public Status apply(Connection connection, Blog data) {
        data.timestamp = generatorAge();
        return Db.executeInsert(connection,"INSERT INTO \"blog\"( title, content, type, link, timestamp) VALUES (?, ?, ?)",data.title,data.content, data.type, data.link, data.timestamp);
    }
}
