package immunity.service;

import immunity.data.blog.Comments;
import immunity.data.basic.IntegerHolder;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction2;

import java.sql.Connection;
import java.util.List;

public class GetComment implements ResultFunction2<Connection,IntegerHolder,List<Comments>> {
    private final toro.Db database = new toro.Db();
    public Result<List<Comments>> apply(Connection connection, IntegerHolder x) {
        return Result.ok(database.queryListObjectFromObjects(connection, "select * from comments where blogid = ?", new Comments(), x.integer));
    }
}
