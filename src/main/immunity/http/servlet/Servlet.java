package immunity.http.servlet;


import immunity.data.blog.Blog;
import immunity.data.core.Function;
import immunity.data.core.Function2;
import immunity.data.core.ResultFunction;
import immunity.data.core.Status;
import immunity.http.marshall.ActionMarshaller;
import immunity.http.marshall.Marshaller;
import immunity.http.marshall.ReturnMarshaller;
import immunity.service.AddBlog;
import immunity.service.GetBlog;
import immunity.db.Connector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Servlet extends HttpServlet {
    private static final Connector connector =  new Connector("jdbc:postgresql://localhost/immunity", "immunity", "pussytown");
    final String blog="/getblog/", addblog="/addblog/";



    private <B>ReturnMarshaller<B> b(ResultFunction<Connection, B> service) {
        return new ReturnMarshaller<B>(service,connector);
    }

    private <A, B> ActionMarshaller<A> actionMarshaller(Class<A> source, Function2<Connection, A, Status> service) {
        return new ActionMarshaller<A>(source, service, connector);
    }

    private ReturnMarshaller<List<Blog>> getblog = b(new GetBlog());
    private ActionMarshaller<Blog> addblogpost = actionMarshaller(Blog.class, new AddBlog());



    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("req.getPathInfo() = " + req.getPathInfo());

        resp.getWriter();
        if (request(req, blog))
            getblog.marshal(resp);
        if (request(req, addblog))
            addblogpost.marshal(req, resp);

    }

    private boolean request(HttpServletRequest request, String path){
        return request.getPathInfo().equals(path);
        //return request.getPathInfo().replaceFirst("^/\\d+", "").equals(path);           //added removal of id from url
    }
}