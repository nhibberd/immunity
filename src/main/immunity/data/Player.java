package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

public class Player implements FromDb<Player> {
    public Integer id;
    public Integer division;

    public Player from(EdgeResultSet edgeResultSet) {
        return new Player(edgeResultSet.getInt(1),edgeResultSet.getInt(2));
    }

    public Player() {
    }

    public Player(Integer id, Integer division) {
        this.id = id;
        this.division = division;
    }
}
