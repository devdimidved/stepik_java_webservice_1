package dbService.dao;

import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDAO {

    private EntityManager entityManager;

    public UsersDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UsersDataSet getUserByLogin(String login) throws HibernateException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteria = builder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> root = criteria.from(UsersDataSet.class);
        criteria.select(root)
                .where(builder.equal(root.get("name"), login));
        UsersDataSet user = null;
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException ignore) {
            Logger.getLogger("").log(Level.INFO, "User " + login + " not found");
        }
        return user;
    }

    public void insertUser(String name, String password) throws HibernateException {
        entityManager.persist(new UsersDataSet(name, password));
    }
}
