package immunity.db;

import immunity.data.blog.Blog;
import immunity.data.core.Action;
import immunity.data.core.Function;
import immunity.data.core.Status;
import immunity.data.core.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static immunity.tool.Validations.checkrownu;

public class Db {
    private final Statement statement = new Statement();

    public void executeUpdateObject(Connection connection, String sql, final Object os) {
        statement.withStatement(connection, sql, new Action<PreparedStatement>() {
            public void apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1, os);
                z.executeUpdate();
            }
        });
    }

    public Status executeUpdateObjects(Connection connection, String sql, final Object... os) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Status>() {
            public Status apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.set(os);
                return checkrownu(z.executeUpdate());
            }
        });
    }

    public void executeUpdate(Connection connection, String sql) {
        statement.withStatement(connection, sql, new Action<PreparedStatement>() {
            public void apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.executeUpdate();
            }
        });
    }

    public Status executeInsert(Connection connection, String sql, final Object... os){
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Status>() {
            public Status apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.set(os);
                return checkrownu(z.executeUpdate());
            }
        });
    }

    public Boolean queryExists(Connection connection, String sql) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Boolean>() {
            public Boolean apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                return resultSet.next();
            }
        });
    }

    public Boolean queryExistsFromObject(Connection connection, String sql, final Object o) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Boolean>() {
            public Boolean apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1, o);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                return resultSet.next();
            }
        });
    }

    public Status queryStatusFromObject(Connection connection, String sql, final Object data){
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Status>() {
            public Status apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1, data);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Status.OK;
                return Status.NOT_FOUND;
            }
        });
    }

    public List<String> queryLoopString(Connection connection, String sql, final Integer columnNumber){
        return statement.withStatement(connection, sql, new Function<PreparedStatement, List<String>>() {
            public List<String> apply(PreparedStatement preparedStatement) {
                List<String> list = new ArrayList<String>();
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                while (resultSet.next()){
                    list.add(resultSet.getString(columnNumber));
                }
                return list;
            }
        });
    }

    public List<Integer> queryLoopInt(Connection connection, String sql, final Integer columnNumber){
        return statement.withStatement(connection, sql, new Function<PreparedStatement, List<Integer>>() {
            public List<Integer> apply(PreparedStatement preparedStatement) {
                List<Integer> list = new ArrayList<Integer>();
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                while (resultSet.next()){
                    list.add(resultSet.getInt(columnNumber));
                }
                return list;
            }
        });
    }

    public List<Blog> queryBlog(Connection connection, String sql){
        return statement.withStatement(connection, sql, new Function<PreparedStatement, List<Blog>>() {
            public List<Blog> apply(PreparedStatement preparedStatement) {
                List<Blog> list = new ArrayList<Blog>();
                EdgePreparedStatement q = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet z = new EdgeResultSet(q);
                while (z.next()) {
                    list.add(new Blog(z.getString(1), z.getString(2), z.getString(3), z.getString(4), z.getLong(5)));
                }
                return list;
            }
        });
    }


    public Result<String> queryStringFromObject(Connection connection, String sql, final Object data, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<String>>() {
            public Result<String> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1,data);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getString(column));
                return Result.notfound();
            }
        });
    }

    public Result<Integer> queryIntFromObject(Connection connection, String sql, final Object data, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<Integer>>() {
            public Result<Integer> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1, data);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getInt(column));
                return Result.notfound();
            }
        });
    }

    public Result<Boolean> queryBoolFromObject(Connection connection, String sql, final Object data, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<Boolean>>() {
            public Result<Boolean> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1,data);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getBoolean(column));
                return Result.notfound();
            }
        });
    }

    public Result<Object> queryObject(Connection connection, String sql, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<Object>>() {
            public Result<Object> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getObject(column));
                return Result.notfound();
            }
        });
    }

    public Result<String> queryString(Connection connection, String sql, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<String>>() {
            public Result<String> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getString(column));
                return Result.notfound();
            }
        });
    }

    public Result<Integer> queryInt(Connection connection, String sql, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<Integer>>() {
            public Result<Integer> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getInt(column));
                return Result.notfound();
            }
        });
    }

    public Result<Boolean> queryBool(Connection connection, String sql, final Integer column) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Result<Boolean>>() {
            public Result<Boolean> apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                EdgeResultSet resultSet = new EdgeResultSet(z);
                if(resultSet.next())
                    return Result.ok(resultSet.getBoolean(column));
                return Result.notfound();
            }
        });
    }

    public Status deleteObject(Connection connection, String sql, final Object data) {
        return statement.withStatement(connection, sql, new Function<PreparedStatement, Status>() {
            public Status apply(PreparedStatement preparedStatement) {
                EdgePreparedStatement z = new EdgePreparedStatement(preparedStatement);
                z.setObject(1, data);
                return checkrownu(z.executeUpdate());
            }
        });
    }
}
