package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

public class Division implements FromDb<Division> {
    public Integer id;
    public String name;
    public String info;
    public String picture;

    public Division from(EdgeResultSet edgeResultSet) {
        return new Division(edgeResultSet.getInt(1),edgeResultSet.getString(2),edgeResultSet.getString(3),
                edgeResultSet.getString(4));
    }

    public Division() {
    }


    public Division(Integer id, String name, String info, String picture) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.picture = picture;
    }


}
