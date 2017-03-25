package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getMysqlConnection();
        createTables();
    }

    public UsersDataSet getUserById(long id) throws DBException {
        try {
            return (new UsersDAO(connection).getUserById(id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public UsersDataSet getUserByLogin(String login) {
        try {
            return (new UsersDAO(connection).getUserByLogin(login));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addUser(String name, String password) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO usersDAO = new UsersDAO(connection);
            usersDAO.insertUser(new UsersDataSet(name, password));
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }


    public void createTables() {
        UsersDAO usersDAO = new UsersDAO(connection);
        try {
            usersDAO.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cleanUp() throws DBException {
        UsersDAO usersDAO = new UsersDAO(connection);
        try {
            usersDAO.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=tully&").          //login
                    append("password=tully");       //password

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
