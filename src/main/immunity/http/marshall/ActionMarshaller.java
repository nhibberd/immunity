package immunity.http.marshall;

import com.google.gson.Gson;
import immunity.data.core.Action;
import immunity.data.core.Action2;
import immunity.data.core.Empty;
import immunity.data.core.Error;
import immunity.db.Connector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class ActionMarshaller<A> {
    private final Gson gson = new Gson();
    private final Empty empty = new Empty();
    private final Class<A> source;
    private final Action2<Connection, A> service;
    private final Connector connector;

    public ActionMarshaller(Class<A> source, Action2<Connection, A> service, Connector connector) {
        this.source = source;
        this.service = service;
        this.connector = connector;
    }

    public void marshal(HttpServletRequest req, HttpServletResponse resp) {
        final A a = read(req);
        try {
            connector.withConnection(new Action<Connection>() {
                public void apply(Connection connection) {
                    service.apply(connection, a);
                }
            });
            write(resp, empty, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            Error error = new Error(e.getClass().getSimpleName(), e.getMessage());
            write(resp, error, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
