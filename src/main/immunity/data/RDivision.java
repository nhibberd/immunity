package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

import java.util.List;

public class RDivision {
    public List<Player> players;
    public String info;

    public RDivision(List<Player> players, String info) {
        this.players = players;
        this.info = info;
    }
}
