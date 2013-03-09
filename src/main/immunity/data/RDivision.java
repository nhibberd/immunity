package immunity.data;

import toro.EdgeResultSet;
import toro.FromDb;

import java.util.List;

public class RDivision {
    public List<Player> players;
    public Division division;

    public RDivision(List<Player> players, Division division) {
        this.players = players;
        this.division = division;
    }
}
