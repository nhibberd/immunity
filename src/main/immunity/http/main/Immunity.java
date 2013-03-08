package immunity.http.main;


import immunity.http.servlet.FileBasicWebServlet;
import immunity.http.servlet.Servlet;
import io.mth.foil.j.*;

import java.io.File;
import java.sql.Connection;


public class Immunity {
    private static final Foils foils = new DefaultFoils();
    private static final Configs c = new DefaultConfigs();


    public static void main(String[] args) {

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