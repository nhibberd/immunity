package immunity.service.auth.password;

import immunity.data.basic.StringHolder;
import immunity.data.core.Function2;
import immunity.data.core.Result;
import immunity.data.core.Status;
import immunity.service.auth.LoginServices;

import java.sql.Connection;

import static immunity.tool.Generators.generatorAge;
import static immunity.tool.Generators.generatorToken;

public class RequestReset implements Function2<Connection,StringHolder,Status> {
    private final immunity.db.Db Db = new immunity.db.Db();
    private final LoginServices service = new LoginServices();

    public Status apply(Connection connection, StringHolder data) {
        final String user = data.string;
        final String token = generatorToken();
        final long age = generatorAge();

        if(!service.checkuser(connection, user))
            return Status.BAD_REQUEST;

        final Result<Integer> id = Result.ok(0);//users.getId(connection,user);
        if (id.statusOK())
            return Db.executeInsert(connection,"INSERT INTO \"password\"( growerid, token, age ) VALUES (?, ?, ?)",id.value(),token, age);
        else
            return Status.NOT_FOUND;
    }
}
