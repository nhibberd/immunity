package immunity.http;

import immunity.data.auth.Cookie;
import immunity.data.auth.Login;
import immunity.data.auth.LoginNugget;
import immunity.data.auth.User;
import immunity.data.core.Function2;
import immunity.data.core.Status;
import immunity.service.auth.LoginServices;
//import immunity.service.auth.UserServices;
import java.sql.Connection;
import java.util.Date;
import static immunity.http.state.LoginState.records;

public class LoginService implements Function2<Connection,Login,Cookie> {
    private final LoginServices service = new LoginServices();

    //private final UserServices userservice = new UserServices();

    private String generatorID(){
        return java.util.UUID.randomUUID().toString();
    }

    public Cookie apply(Connection connection, Login login) {
        if((service.authenticate(connection, login.user, login.password)) == Status.OK){
            String id = generatorID();
            //User data = userservice.getUser(connection, login.user);
            LoginNugget creds = new LoginNugget( login.user, login.password, new Date().getTime());
            records.put(id,creds);
            //return new Cookie(grower,id);
            return new Cookie(id);
        }
        return null;
    }
}
