package immunity.data.blog;

import toro.EdgeResultSet;
import toro.FromDb;

public class Comments implements FromDb<Comments> {
    public Integer blogid;
    public Integer postid;
    public Integer authorid;
    public String content;
    public long timestamp;


    public Comments from(EdgeResultSet edgeResultSet) {
        return new Comments(edgeResultSet.getInt(1),edgeResultSet.getInt(2),edgeResultSet.getInt(3),edgeResultSet.getString(4),
                edgeResultSet.getLong(5));
    }

    public Comments() {
    }


    public Comments(Integer blogid, Integer postid, Integer authorid, String content, long timestamp) {
        this.blogid = blogid;
        this.postid = postid;
        this.authorid = authorid;
        this.content = content;
        this.timestamp = timestamp;
    }
}
