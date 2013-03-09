package immunity.service;

import immunity.data.Division;
import immunity.data.Player;
import immunity.data.RDivision;
import immunity.data.basic.IntegerHolder;
import immunity.data.basic.StringHolder;
import immunity.data.blog.Blog;
import immunity.data.core.Result;
import immunity.data.core.ResultFunction;
import immunity.data.core.ResultFunction2;
import toro.Option;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class GetDivision implements ResultFunction2<Connection,StringHolder,RDivision> {
    private final toro.Db database = new toro.Db();
    public Result<RDivision> apply(Connection connection, StringHolder x) {
        Option<Division> division = database.queryObjectFromObjects(connection, "select * from division where name=?", new Division(), x.string);
        if (division.isSome()) {
            List<Player> players = database.queryListObjectFromObjects(connection, "select * from player where division=?", new Player(), division.getOrDie().id);
            return Result.ok(new RDivision(players, division.getOrDie()));
        }
        else
            return Result.notfound();
    }
}
