package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction;

import java.sql.Connection;
import java.util.List;

public class GetBlog implements ResultFunction<Connection,List<Blog>> {
    private final immunity.db.Db Db = new immunity.db.Db();
    public Result<List<Blog>> apply(Connection connection) {
        List<Blog> r = Db.queryBlog(connection,"SELECT * FROM \"blog\"");
        if (r.size() >= 1)
            return Result.ok(r);
        else
            return Result.notfound();
    }
}
