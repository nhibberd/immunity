package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

public class Sponsor implements FromDb<Sponsor> {
    public Integer id;
    public Integer position;
    public String name;
    public String picture;
    public String info;


    public Sponsor from(EdgeResultSet edgeResultSet) {
        return new Sponsor(edgeResultSet.getInt(1),edgeResultSet.getInt(2), edgeResultSet.getString(3),
                edgeResultSet.getString(4), edgeResultSet.getString(5));
    }

    public Sponsor() {
    }

    public Sponsor(Integer id, Integer position, String name, String picture, String info) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.picture = picture;
        this.info = info;
    }
}
