package immunity.service;

import immunity.data.Sponsor;
import immunity.data.blog.Blog;

import java.util.Comparator;

public class SponsorComparator implements Comparator<Sponsor> {
    public int compare(Sponsor o1, Sponsor o2) {
        return o1.position.compareTo(o2.position);
    }
}
