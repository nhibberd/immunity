package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

public class Division implements FromDb<Division> {
    public Integer id;
    public String name;
    public String info;

    public Division from(EdgeResultSet edgeResultSet) {
        return new Division(edgeResultSet.getInt(1),edgeResultSet.getString(2),edgeResultSet.getString(3));
    }

    public Division() {
    }

    public Division(Integer id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

}
