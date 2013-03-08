package immunity.service;

import immunity.data.blog.Blog;
import immunity.data.database.Patch;

import java.util.Comparator;

public class BlogComparator implements Comparator<Blog> {
    public int compare(Blog oldest, Blog last) {
        return last.timestamp.compareTo(oldest.timestamp);
    }
}
