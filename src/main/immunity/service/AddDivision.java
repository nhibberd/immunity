package immunity.service;

import immunity.data.Division;
import immunity.data.core.Action2;

import java.sql.Connection;

public class AddDivision implements Action2<Connection,Division> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Division data) {
        database.updateObjects(connection,"INSERT INTO \"division\"( name, info, picture ) VALUES (?, ?, ?)",
                data.name, data.info, data.picture);
    }
}
