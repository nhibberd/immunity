package immunity.service;

import immunity.data.Player;
import immunity.data.core.Action2;

import java.sql.Connection;

public class AddPlayer implements Action2<Connection,Player> {
    private final toro.Db database = new toro.Db();

    public void apply(Connection connection, Player data) {
        database.updateObjects(connection,"insert into \"player\"( id, division, name, bio, picture) VALUES (?, ?, ?, ?, ?)",
                data.id,data.division, data.name, data.bio, data.picture);
    }
}
