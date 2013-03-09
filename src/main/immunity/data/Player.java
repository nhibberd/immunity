package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

public class Player implements FromDb<Player> {
    public Integer id;
    public Integer division;
    public String name;
    public String bio;
    public String picture;

    public Player from(EdgeResultSet edgeResultSet) {
        return new Player(edgeResultSet.getInt(1),edgeResultSet.getInt(2), edgeResultSet.getString(3),
                edgeResultSet.getString(4), edgeResultSet.getString(5));
    }

    public Player() {
    }

    public Player(Integer id, Integer division, String name, String bio, String picture) {
        this.id = id;
        this.division = division;
        this.name = name;
        this.bio = bio;
        this.picture = picture;
    }

}
