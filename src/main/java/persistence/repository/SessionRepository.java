package persistence.repository;

import persistence.entity.Session;

public interface SessionRepository {
    void addNewSession(Session session);
    public void deleteSession(String uuid);
}
