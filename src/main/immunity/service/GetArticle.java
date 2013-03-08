package immunity.service;

import immunity.data.basic.IntegerHolder;
import immunity.data.blog.Article;
import immunity.data.blog.Blog;
import immunity.data.blog.Comments;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction2;
import toro.Option;

import java.sql.Connection;
import java.util.List;

public class GetArticle implements ResultFunction2<Connection,IntegerHolder,Article> {
    private final toro.Db database = new toro.Db();
    public Result<Article> apply(Connection connection, IntegerHolder x) {
        Option<Blog> r = database.queryObjectFromObjects(connection, "select * from blog where id = ?", new Blog(), x.integer);
        Result<List<Comments>> comments = new GetComment().apply(connection, x);
        if (r.isSome())
            return Result.ok(new Article(r.getOrDie(), comments.value()));
        else
            return Result.notfound();
    }
}