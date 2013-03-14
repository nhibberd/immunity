package immunity.data.auth;

import toro.EdgeResultSet;
import toro.FromDb;

public class User implements FromDb<User> {
    public Integer id;
    public String username;
    public String email;
    public byte[] password;
    public byte[] salt;
    public Boolean admin;
    public Boolean content;
    public Boolean moderator;

    public User(Integer id, String username, String email, byte[] password, byte[] salt, Boolean admin, Boolean content, Boolean moderator) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.admin = admin;
        this.content = content;
        this.moderator = moderator;
    }

    public User() {
    }

    @Override
    public User from(EdgeResultSet edgeResultSet) {
        return null;
    }
}

