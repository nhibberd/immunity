package immunity.http.main;


import immunity.data.Division;
import immunity.data.Player;
import immunity.data.Sponsor;
import immunity.data.blog.Blog;
import immunity.data.blog.Comments;
import immunity.data.core.Action;
import immunity.data.database.Versions;
import immunity.db.Connector;
import immunity.http.servlet.FileBasicWebServlet;
import immunity.http.servlet.Servlet;
import immunity.service.*;
import io.mth.foil.j.*;

import java.sql.Connection;

import static immunity.tool.Generators.generatorAge;


public class Immunity {
    private static final Foils foils = new DefaultFoils();
    private static final Configs c = new DefaultConfigs();


    public static void main(String[] args) {
        Connector connector = new Connector("jdbc:postgresql://localhost/immunity", "immunity", "pussytown");
        final Versions db = new Versions();
        db.go();

        connector.withConnection(new Action<Connection>() {
            public void apply(Connection connection) {
                if (db.getCurrent(connection) == 0)
                    System.exit(1);
            }
        });


        connector.withConnection(new Action<Connection>() {
            public void apply(Connection connection) {
                try {
                    final toro.Db database = new toro.Db();
                    AddBlog addBlog = new AddBlog();
                    addBlog.apply(connection, new Blog("BEZ", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,", "article", "", (long) 0, 0,0  ));
                    AddComment addComment = new AddComment();
                    String dmp = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s,";
                    addComment.apply(connection, new Comments(1,1,0,dmp,(long) 0));
                    addComment.apply(connection, new Comments(1,2,0,dmp,(long) 0));
                    addComment.apply(connection, new Comments(1,3,0,dmp,(long) 0));
                    addComment.apply(connection, new Comments(1, 4, 0, dmp, (long) 0));
                    AddDivision addDivision = new AddDivision();
                    addDivision.apply(connection, new Division(0,"csgo","this game is bananas", "./img/game_csgo.png"));
                    addDivision.apply(connection, new Division(1, "bf3", "this game is sux", "./img/game_bf3.png"));
                    AddPlayer addPlayer = new AddPlayer();
                    addPlayer.apply(connection, new Player(1, 1, "NAME 'obez' NAME", "hello", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(2, 1, "NAME 'nick' NAME", "hello", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(3, 1, "NAME 'boomser' NAME", "hello", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(4, 1, "NAME 'deathdog' NAME", "hello", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(5, 1, "NAME 'yellow' NAME", "hello", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(6, 2, "NAME 'alias' NAME", "helxxxxlo", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(7, 2, "NAME 'alias' NAME", "helxxxxlo", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(8, 2, "NAME 'alias' NAME", "helxxxxlo", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(9, 2, "NAME 'alias' NAME", "helxxxxlo", "./img/photo.png"));
                    addPlayer.apply(connection, new Player(10, 2, "NAME 'alias' NAME", "helxxxxlo", "./img/photo.png"));
                    AddSponsor addSponsor = new AddSponsor();
                    addSponsor.apply(connection, new Sponsor(0,1,"Intel Australia", "./img/sponsor_intel.png", "dis shit is real"));
                    addSponsor.apply(connection, new Sponsor(0,2,"BENQ Australia", "./img/sponsor_benq.png", "dis shit is real"));
                    addSponsor.apply(connection, new Sponsor(0,3,"ANTEC Australia", "./img/sponsor_antec.png", "dis shit is real"));
                    addSponsor.apply(connection, new Sponsor(0,4,"RED BULL Australia", "./img/sponsor_redbull.png", "dis shit is real"));
                    addSponsor.apply(connection, new Sponsor(0,5,"GEIL Australia", "./img/sponsor_geil.png", "dis shit is real"));
                    addSponsor.apply(connection, new Sponsor(0,5,"Steel seriesasdasas Australia", "./img/sponsor_steelseries.png", "dis shit is real"));


                } catch (Exception ignored) {
                }
            }
        });

        io.mth.foil.j.Config config = c.compound(
                c.servlet("/immunity", "/*", new Servlet()),
                c.servlet( "/", "/*", new FileBasicWebServlet())
        );

        Foil foil = foils.nu("immunity", 10080, config);
        try {
            foil.run();
        } finally {
            System.out.println("end");
            //threads.stop();
        }
    }
}