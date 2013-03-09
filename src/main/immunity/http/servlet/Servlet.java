package immunity.http.servlet;


import immunity.data.Division;
import immunity.data.Player;
import immunity.data.RDivision;
import immunity.data.Sponsor;
import immunity.data.basic.StringHolder;
import immunity.data.blog.Article;
import immunity.data.blog.Comments;
import immunity.data.basic.IntegerHolder;
import immunity.data.blog.Blog;
import immunity.data.core.*;
import immunity.http.marshall.ActionMarshaller;
import immunity.http.marshall.ResultFunctionMarshaller;
import immunity.http.marshall.ReturnMarshaller;
import immunity.service.AddBlog;
import immunity.service.*;
import immunity.db.Connector;
import immunity.service.GetArticle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Servlet extends HttpServlet {
    private static final Connector connector =  new Connector("jdbc:postgresql://localhost/immunity", "immunity", "pussytown");
    final String blog="/getblog/", addblog="/addblog/", article="/getarticle/", acomment="/addcomment/",
        gcomment="/getcomment/", gdivision="/getdivision/", aplayer="/addplayer/", adivision="/adddivision/",
        asponsor="/addsponsor/", gsponsor="/getsponsors/";


    private <B>ReturnMarshaller<B> b(ResultFunction<Connection, B> service) {
        return new ReturnMarshaller<B>(service,connector);
    }

    private <A, B> ActionMarshaller<A> actionMarshaller(Class<A> source, Action2<Connection, A> service) {
        return new ActionMarshaller<A>(source, service, connector);
    }

    private <A, B> ResultFunctionMarshaller<A, B> r(Class<A> source, ResultFunction2<Connection, A, B> service) {
        return new ResultFunctionMarshaller<A, B>(source, service, connector);
    }

    private ReturnMarshaller<List<Blog>> getblog = b(new GetBlog());
    private ResultFunctionMarshaller<IntegerHolder, Article> getarticle = r(IntegerHolder.class, new GetArticle());
    private ActionMarshaller<Blog> addblogpost = actionMarshaller(Blog.class, new AddBlog());
    private ActionMarshaller<Comments> addcomment = actionMarshaller(Comments.class, new AddComment());
    private ResultFunctionMarshaller<IntegerHolder, List<Comments>> getcomment = r(IntegerHolder.class, new GetComment());
    private ResultFunctionMarshaller<StringHolder, RDivision> getdivision = r(StringHolder.class, new GetDivision());

    private ActionMarshaller<Division> adddivision = actionMarshaller(Division.class, new AddDivision());
    private ActionMarshaller<Player> addplayer = actionMarshaller(Player.class, new AddPlayer());

    private ActionMarshaller<Sponsor> addsponsor = actionMarshaller(Sponsor.class, new AddSponsor());
    private ReturnMarshaller<List<Sponsor>> getsponsor = b(new GetSponsor());



    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (request(req, blog))
            getblog.marshal(resp);
        if (request(req, addblog))
            addblogpost.marshal(req, resp);
       if (request(req, article))
            getarticle.marshal(req, resp);
       if (request(req, acomment))
           addcomment.marshal(req, resp);
       if (request(req, gcomment))
           getcomment.marshal(req, resp);
       if (request(req, gdivision))
           getdivision.marshal(req, resp);
       if (request(req, adivision))
           adddivision.marshal(req, resp);
       if (request(req, aplayer))
           addplayer.marshal(req, resp);
       if (request(req, asponsor))
           addsponsor.marshal(req, resp);
       if (request(req, gsponsor))
           getsponsor.marshal(resp);

    }

    private boolean request(HttpServletRequest request, String path){
        return request.getPathInfo().equals(path);
    }
}