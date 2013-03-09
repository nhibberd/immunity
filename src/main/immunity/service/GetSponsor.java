package immunity.service;

import immunity.data.Sponsor;
import immunity.data.blog.Blog;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class GetSponsor implements ResultFunction<Connection,List<Sponsor>> {
    private final toro.Db database = new toro.Db();
    public Result<List<Sponsor>> apply(Connection connection) {
        List<Sponsor> r = database.queryListObject(connection, "select * from sponsors", new Sponsor());
        if (r.size() >= 1){
            Collections.sort(r, new SponsorComparator());
            return Result.ok(r);
        }
        else
            return Result.notfound();
    }
}
