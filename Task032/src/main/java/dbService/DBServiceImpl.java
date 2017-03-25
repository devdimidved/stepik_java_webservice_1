package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DBServiceImpl implements DBService {
    private final EntityManagerFactory entityManagerFactory;

    public DBServiceImpl() {
        entityManagerFactory = createEntityManagerFactory();
    }

    public UsersDataSet getUserByLogin(String login) throws DBException {
        try {
            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            UsersDAO dao = new UsersDAO(entityManager1);
            return dao.getUserByLogin(login);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void addUser(String name, String password) throws DBException {
        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            txn = entityManager.getTransaction();
            txn.begin();
            UsersDAO dao = new UsersDAO(entityManager);
            dao.insertUser(name, password);
            txn.commit();
        } catch (HibernateException e) {
            if ( txn != null && txn.isActive()) txn.rollback();
            throw new DBException(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("stepik_Java_Web_Service_1_3_2");
        return emf;
    }
}
