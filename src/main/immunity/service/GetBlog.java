package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction;
import toro.Option;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class GetBlog implements ResultFunction<Connection,List<Blog>> {
    private final toro.Db database = new toro.Db();
    public Result<List<Blog>> apply(Connection connection) {
        List<Blog> r = database.queryListObject(connection, "select * from blog", new Blog());
        if (r.size() >= 1){
            Collections.sort(r, new BlogComparator());
            return Result.ok(r);
        }
        else
            return Result.notfound();
    }
}
