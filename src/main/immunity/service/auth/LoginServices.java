package immunity.service.auth;

import immunity.data.auth.Token;
import immunity.data.auth.TokenAge;
import immunity.data.core.Function;
import immunity.data.core.Result;
import immunity.data.core.Status;
import immunity.data.auth.User;
import immunity.db.Connector;
import immunity.db.EdgePreparedStatement;
import immunity.db.EdgeResultSet;
import immunity.db.Statement;
import toro.Option;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import static immunity.tool.Base64.base64ToByte;
import static toro.Getters.*;


public class LoginServices {
    private final toro.Db database = new toro.Db();
    public static final Statement statement = new Statement();
    public static final Connector connector = new Connector("jdbc:postgresql://localhost/immunity", "immunity", "pussytown");

    public Boolean authenticatebool(final String user, final String password) {
        return connector.withConnection(new Function<Connection, Boolean>() {
            public Boolean apply(Connection connection) {
                return authenticate(connection, user, password) == Status.OK;
            }
        });
    }

    public Status authenticate(final String user, final String password) {
        return connector.withConnection(new Function<Connection, Status>() {
            public Status apply(Connection connection) {
                return authenticate(connection,user,password);
            }
        });
    }

    public Boolean authenticate(Connection connection, final Integer id, final String password) {
        String user = getuser(connection, id);
        return user != null && authenticate(connection, user, password) == Status.OK;
    }

    public Status authenticate(Connection connection, final String user, final String password) {
        final String sql = "SELECT * FROM \"users\" WHERE \"username\" = ?";
        if(user!=null && password!=null && user.length()<=100) {
            if (!checkuser(connection, user))
                return Status.NOT_FOUND;
            byte[] proposedDigest;
            Option<User> z = database.queryObjectFromObjects(connection, sql,new User(),user);
            if (z.isSome()) {
                proposedDigest = getHash(password,z.getOrDie().salt);
                if(Arrays.equals(proposedDigest, z.getOrDie().password))
                    return Status.OK;
            }
            else
                return Status.NOT_AUTH;
        }
        return Status.BAD_REQUEST;
    }

    public String getuser(Connection connection, final Integer id){
        Option<String> z = database.queryFromObjects(connection, "SELECT * FROM \"users\" WHERE \"id\" = ?", getString, id);
        if (z.isSome())
            return z.getOrDie();
        else
            return "";
    }

    public Boolean checkuser(Connection connection, final String user){
        return database.queryExistsFromObjects(connection, "SELECT * FROM \"users\" WHERE \"username\" = ?", user);
    }

    public Boolean checkuser(Connection connection, final Integer id){
        return database.queryExistsFromObjects(connection, "SELECT * FROM \"users\" WHERE \"id\" = ?", id);
    }

    public byte[] getHash(String password, byte[] salt)  {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            return digest.digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public TokenAge tokenResult(EdgePreparedStatement q) {
        EdgeResultSet resultSet = new EdgeResultSet(q);
        Integer number = null;
        String token = null;
        Long age = null;
        if (resultSet.next()) {
            number = resultSet.getInt(1);
            token = resultSet.getString(2);
            age = resultSet.getLong(3);
        }
        if (age==null)
            age=(long)0;
        return new TokenAge(new Token(number, token), age);
    }
}