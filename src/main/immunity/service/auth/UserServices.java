package immunity.service.auth;

import immunity.data.auth.User;
import immunity.data.core.Function;
import immunity.data.core.Result;
import immunity.data.core.Status;
import immunity.db.EdgePreparedStatement;
import immunity.db.Statement;
import toro.Option;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static immunity.tool.Base64.byteToBase64;
import static toro.Getters.*;

public class UserServices {
    private final LoginServices service = new LoginServices();
    private static final SecureRandom generator = random();
    private final toro.Db database = new toro.Db();
    public static final Statement statement = new Statement();

    public User getUser(Connection connection, final String user){
        final String sql = "SELECT * FROM \"users\" WHERE \"username\" = ?";
        Option<User> r = database.queryObjectFromObjects(connection, sql, new User(), user);
        return r.getOr(new User());
    }

    public Result<Integer> getId(Connection connection, final String user){
        Option<Integer> r = database.queryFromObjects(connection, "SELECT \"id\" FROM \"users\" WHERE \"username\" = ?", getInteger, user);
        if (r.isSome())
            return Result.ok(r.getOrDie());
        else
            return Result.notfound();
    }

    public Boolean exists(Connection connection, final Integer id){
        return database.queryExistsFromObjects(connection,"SELECT * FROM \"users\" WHERE \"id\" = ?",id);
    }

    public Boolean exists(Connection connection, final String user){
        return database.queryExistsFromObjects(connection, "SELECT * FROM \"users\" WHERE \"username\" = ?", user);
    }

    public User getUser(Connection connection, final Integer id){
        final String sql = "SELECT * FROM \"users\" WHERE \"id\" = ?";
        Option<User> r = database.queryObjectFromObjects(connection, sql, new User(), id);
        return r.getOr(new User());
    }

    private String encrypt(String password, byte[] salt){
        byte[] passDigest = service.getHash(password, salt);
        return byteToBase64(passDigest);
    }

    private static SecureRandom random() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

}
