package dbService;

import dbService.dataSets.UsersDataSet;

public interface DBService {
    UsersDataSet getUserByLogin(String login) throws DBException;
    void addUser(String name, String password) throws DBException;
}
