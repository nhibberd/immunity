package immunity.service;

import immunity.data.blog.Comments;
import immunity.data.core.Action2;

import java.sql.Connection;

import static immunity.tool.Generators.generatorAge;

public class AddComment implements Action2<Connection, Comments> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Comments data) {
        data.timestamp = generatorAge();
        database.updateObjects(connection,"insert into \"comments\"( blogid, postid, authorid, content, timestamp) VALUES (?, ?, ?, ?, ?)",
                data.blogid,data.postid,data.authorid,data.content,data.timestamp);


    }
}
