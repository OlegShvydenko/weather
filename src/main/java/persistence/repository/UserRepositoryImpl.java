package persistence.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import persistence.entity.User;
import util.HibernateUtil;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final Session session;

    public UserRepositoryImpl(Session session) {
        this.session = session;
    }
    public UserRepositoryImpl() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public Optional<User> getUserByLogin(String login){
        Query<User> query = session.createQuery("from User where login = :login", User.class);
        query.setParameter("login", login);
        return query.uniqueResultOptional();
    }
    public void addNewUser(User user){

        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
    }
    public String getPasswordHash(String login){
        Query<User> query = session.createQuery("from User where login = :login", User.class);
        query.setParameter("login", login);
        return query.uniqueResult().getPassword();
    }
}
