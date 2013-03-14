package immunity.service.auth.password;

import immunity.data.auth.Token;
import immunity.data.auth.TokenAge;
import immunity.data.core.Function2;
import immunity.data.core.Status;
import immunity.service.auth.LoginServices;
import toro.Option;

import java.sql.Connection;

import static immunity.tool.Dates.HOUR;
import static immunity.tool.Validations.checkAge;

public class CheckReset implements Function2<Connection,Token,Status> {
    LoginServices service = new LoginServices();
    private final toro.Db database = new toro.Db();

        //check password db for grower number and relevant token

    public Status apply(Connection connection, final Token input) {
        final String sql = "SELECT * FROM password WHERE growerid = ?";
        Option<TokenAge> r = database.queryObjectFromObjects(connection,sql, new TokenAge(),input.number);
        if (r.isSome()){
            TokenAge z = r.getOrDie();
            if (input.token.equals(z.token.token) && input.number.equals(z.token.number) && checkAge(z.age,2 * HOUR))
                return Status.OK;
            else if (checkAge(z.age,2 * HOUR))
                return Status.NOT_FOUND;

        }   else {
            return Status.NOT_AUTH;
        }
        return Status.NOT_FOUND;
    }
}