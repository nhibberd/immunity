package immunity.service;

import immunity.data.Division;
import immunity.data.Sponsor;
import immunity.data.core.Action2;

import java.sql.Connection;

public class AddSponsor implements Action2<Connection,Sponsor> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Sponsor data) {
        database.updateObjects(connection,"INSERT INTO \"sponsors\"( position, name, picture, info) VALUES (?, ?, ?, ?)",
                data.position, data.name, data.picture, data.info);
    }
}
