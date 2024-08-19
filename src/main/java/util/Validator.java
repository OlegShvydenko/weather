package util;

import persistence.repository.UserRepository;
import persistence.repository.UserRepositoryImpl;

import java.util.Objects;

public class Validator {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final UserRepository userRepository;

    public Validator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Validator() {
        this.userRepository = new UserRepositoryImpl();
    }

    public boolean checkValidityOfLogin(String login) {
        return Objects.equals(login, "");
    }

    public boolean checkExistenceOfLogin(String login) {
        return userRepository.getUserByLogin(login).isPresent();
    }

    public boolean checkIdentifyOfPasswords(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }
    public boolean checkPasswordLength(String password){
        return (password.length() >= MIN_PASSWORD_LENGTH);
    }
}
