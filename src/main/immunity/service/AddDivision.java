package immunity.service;

import immunity.data.Division;
import immunity.data.blog.Blog;
import immunity.data.core.Action2;

import java.sql.Connection;

import static immunity.tool.Generators.generatorAge;

public class AddDivision implements Action2<Connection,Division> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Division data) {
        database.updateObjects(connection,"INSERT INTO \"division\"( name, info ) VALUES (?, ?)",
                data.name, data.info);
    }
}
