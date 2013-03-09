package immunity.data.database;


import java.util.Arrays;
import java.util.List;

public class VersionOne implements Patch {
    public Integer version() {
        return 1;
    }
    public List<String> changes() {
        return Arrays.asList(
                "create table if not exists blog ( title varchar(250), content varchar(5000), type varchar(50), link varchar(250), timestamp BIGINT, id SERIAL UNIQUE, authorid INTEGER )",
                "create table if not exists division ( id SERIAL UNIQUE, name varchar(50), info varchar(1024), picture varchar(50) )",
                "create table if not exists comments ( blogid INTEGER, postid INTEGER, authorid INTEGER, content varchar(5000), timestamp BIGINT )",
                "create table if not exists player ( id INTEGER, division INTEGER, name varchar(50), bio varchar(2000), picture varchar(50) )",
                "create table if not exists sponsors ( id SERIAL UNIQUE, position INTEGER, name varchar(50), picture varchar(50), info varchar(1024) )"
                );
    }
}