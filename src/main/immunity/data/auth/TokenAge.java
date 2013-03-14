package immunity.data.auth;

import toro.EdgeResultSet;
import toro.FromDb;

public class TokenAge implements FromDb<TokenAge> {
    public Token token;
    public long age;

    public TokenAge(Token token, long age) {
        this.age = age;
        this.token = token;
    }

    public TokenAge() {
    }

    public TokenAge from(EdgeResultSet edgeResultSet) {
        return new TokenAge(new Token(edgeResultSet.getInt(1), edgeResultSet.getString(2)),edgeResultSet.getLong(3));
    }
}
