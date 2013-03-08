package immunity.data.database;

import toro.Action;
import toro.Connector;
import toro.Db;

import java.sql.Connection;

public class Init {
    private static final Connector connector = new Connector("jdbc:postgresql://localhost/immunity", "immunity", "pussytown");

    static Db database = new Db();

    public void asd() {
        connector.withConnection(new Action<Connection>(){
            public void apply(Connection connection) {
                database.executeUpdate(connection,"create table blog ( title varchar(250), content varchar(1024), type varchar(50), link varchar(250), timestamp BIGINT )");
            }
        });
    }

}
