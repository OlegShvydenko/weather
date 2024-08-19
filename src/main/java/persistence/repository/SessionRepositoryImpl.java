package persistence.repository;

import org.hibernate.Transaction;
import persistence.entity.Session;
import util.HibernateUtil;

public class SessionRepositoryImpl implements SessionRepository{
    private final org.hibernate.Session session;

    public SessionRepositoryImpl(org.hibernate.Session session) {
        this.session = session;
    }
    public SessionRepositoryImpl() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void addNewSession(Session entitySession) {
        Transaction transaction = session.beginTransaction();
        session.persist(entitySession);
        transaction.commit();
    }
    public void deleteSession(String uuid){
        Transaction transaction = session.beginTransaction();
        Session entitySession = session.get(Session.class, uuid);
        if (entitySession != null) {
            session.remove(entitySession);
            transaction.commit();
        }
    }
}
