package persistence.repository;

import persistence.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserByLogin(String login);
    void addNewUser(User user);
    String getPasswordHash(String login);
}
