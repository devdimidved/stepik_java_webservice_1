package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet getUserById(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public UsersDataSet getUserByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + login + "'", result -> {
            if (result.next()) {
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
            } else {
                return null;
            }
        });
    }

    public void insertUser(UsersDataSet user) throws SQLException {
        executor.execUpdate("insert into users (user_name, password) values ('" + user.getName() + "', '" + user.getPassword() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, user_name varchar(256), password varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
