package immunity.http.state;

import immunity.data.auth.LoginNugget;

import java.util.concurrent.ConcurrentHashMap;

public class LoginState {
    public static final ConcurrentHashMap<String, LoginNugget> records =  new ConcurrentHashMap<String, LoginNugget>();
}
