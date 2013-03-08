package immunity.data.database;

import java.util.List;

public interface Patch {
    Integer version();
    List<String> changes();
}
