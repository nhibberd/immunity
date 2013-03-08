package immunity.http.marshall;

import immunity.data.blog.Blog;
import immunity.data.core.*;
import com.google.gson.Gson;
import immunity.data.core.Error;
import immunity.db.Connector;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnMarshaller<B> {
    private final Gson gson = new Gson();
    private final Empty empty = new Empty();
    private final ResultFunction<Connection, B> service;
    private final Connector connector;

    public ReturnMarshaller(ResultFunction<Connection, B> service, Connector connector) {
        this.service = service;
        this.connector = connector;
    }
    
    public void marshal(HttpServletResponse resp) {
        try {
            String dmp = "Earlier this week we let slip that our CS:GO team were in Queensland. It wasn't just for aasdasdasda <p> asdasda sd </p> humble tournament at a netcafe - you should have known to expect more! Our players are locked away in a super-secret practice facility in an undisclosed part of Brisbane city, hard at work developing their strategies and refining already razor-sharp reflexes. When they emerge, they'll make for well-oiled cohesive unit of superb cyber-athletes whose one objective will be complete world domination - and it all started with a sausage sizzle. You've been warned! A huge shoutout to Laze for aiding us in documenting the trip so far. Have a look through the album to get a glimpse inside the first CS:GO Bootcamp for 2013 - everyone's AK better have a name by the end of the week!";

            List<Blog> x = new ArrayList<Blog>();
            x.add(new Blog("CS:GO Bootcamp 2013", dmp, "article","", new Date().getTime()));
            x.add(new Blog("OBEZ!", "LOL NO.", "feature", "", new Date().getTime()));
            x.add(new Blog("Team Immunity WINS IEM!", "ASDASDSD", "video", "'http://www.youtube.com/embed/DQYPnsKWAvI??html5=1'", new Date().getTime()));
            write(resp, x, HttpServletResponse.SC_OK);




            /*Result<B> b = connector.withConnection(new Function<Connection, Result<B>>() {
                public Result<B> apply(Connection connection) {
                    return service.apply(connection);
                }

            if (b.status() == Status.OK)
                write(resp, b.value(), HttpServletResponse.SC_OK);
            else if (b.status() == Status.NOT_FOUND)
                write(resp, empty, HttpServletResponse.SC_NOT_FOUND);
            else if (b.status() == Status.NOT_AUTH)
                write(resp, empty, HttpServletResponse.SC_UNAUTHORIZED);
            else if (b.status() == Status.BAD_REQUEST)
                write(resp, empty, HttpServletResponse.SC_BAD_REQUEST);
            });   */
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
}
