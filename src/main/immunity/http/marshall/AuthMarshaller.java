package immunity.http.marshall;

import com.google.gson.Gson;
import immunity.data.core.Empty;
import immunity.data.core.Error;
import immunity.data.core.Function;
import immunity.data.core.Function2;
import immunity.db.Connector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class AuthMarshaller<A, B> {
    private final Gson gson = new Gson();
    private final Empty empty = new Empty();
    private final Class<A> source;
    private final Function2<Connection, A, B> service;
    private final Connector connector;

    public AuthMarshaller(Class<A> source, Function2<Connection, A, B> service, Connector connector) {
        this.source = source;
        this.service = service;
        this.connector = connector;
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) {
        final A a = read(req);
        try {
            B b = connector.withConnection(new Function<Connection, B>() {
                public B apply(Connection connection) {
                    return service.apply(connection, a);
                }
            });
            immunity.data.auth.Cookie z = (immunity.data.auth.Cookie) b;
            if (b!=null){
                Cookie userCookie = new Cookie("immunity", z.uid);
                //userCookie.setSecure(true);         //todo wrap with test for https
                userCookie.setPath("/");
                resp.addCookie(userCookie);
                write(resp, z.uid, HttpServletResponse.SC_OK);
            }
            else
                write(resp, empty, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            immunity.data.core.Error error = new Error(e.getClass().getSimpleName(), e.getMessage());
            write(resp, error, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public void notauth(HttpServletResponse resp) {
        write(resp, empty, HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void write(HttpServletResponse resp, Object o, int status) {
        String json = gson.toJson(o);
        PrintWriter writer = writer(resp);
        resp.setStatus(status);
        resp.setContentType("text/json");
        writer.println(json);
    }

    private PrintWriter writer(HttpServletResponse resp) {
        try {
            return resp.getWriter();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private A read(HttpServletRequest req) {
        BufferedReader reader = reader(req);
        return gson.fromJson(reader, source);
    }

    private BufferedReader reader(HttpServletRequest req) {
        try {
            return req.getReader();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
