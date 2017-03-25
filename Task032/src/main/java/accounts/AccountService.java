package accounts;

import java.util.HashMap;
import java.util.Map;
import dbService.DBException;
import dbService.DBService;
import dbService.DBServiceImpl;
import dbService.dataSets.UsersDataSet;

public class AccountService {
    private final Map<String, UsersDataSet> sessionIdToUsersDataSet;
    private final DBService dbService;

    public AccountService() {
        sessionIdToUsersDataSet = new HashMap<>();
        dbService = new DBServiceImpl();
    }

    public void addNewUser(String login, String password) throws DBException {
        dbService.addUser(login, password);
    }

    public UsersDataSet getUserByLogin(String login) throws DBException {
        return dbService.getUserByLogin(login);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToUsersDataSet.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet user) {
        sessionIdToUsersDataSet.put(sessionId, user);
    }

    public void deleteSession(String sessionId) {
        sessionIdToUsersDataSet.remove(sessionId);
    }
}
