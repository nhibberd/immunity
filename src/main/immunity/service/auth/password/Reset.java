package immunity.service.auth.password;

import immunity.data.core.Function2;
import immunity.data.core.Result;
import immunity.data.core.Status;
import immunity.data.user.ResetPassword;
import immunity.service.auth.LoginServices;

import java.sql.Connection;

import static immunity.tool.Base64.base64ToByte;
import static immunity.tool.Base64.byteToBase64;

public class Reset implements Function2<Connection,ResetPassword,Status> {
    private final immunity.db.Db Db = new immunity.db.Db();
    private final LoginServices service = new LoginServices();


    public Status apply(Connection connection, final ResetPassword details) {
        if ( details.password!=null && details.password2!=null && details.password.equals(details.password2)){
            Result<Integer> i = Db.queryIntFromObject(connection,"SELECT * FROM \"password\" WHERE \"token\" = ?",details.token,1);
            if (i.statusOK()) {
                final Integer id = i.value();
                Result<String> salt = Db.queryStringFromObject(connection,"SELECT salt FROM \"users\" WHERE \"growerid\" = ?",id,1);
                if (salt.statusOK()){
                    final String password = byteToBase64(service.getHash(details.password, base64ToByte(salt.value())));
                    Db.executeUpdateObjects(connection,"UPDATE \"users\" SET \"password\" = ? WHERE \"growerid\" = ?",password,id);
                    return Status.OK;
                }
            }
            return Status.NOT_FOUND;
        }
        else
            return Status.BAD_REQUEST;
    }
}
