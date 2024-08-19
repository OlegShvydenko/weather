package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.entity.Session;
import persistence.entity.User;
import persistence.repository.SessionRepository;
import persistence.repository.SessionRepositoryImpl;
import persistence.repository.UserRepository;
import persistence.repository.UserRepositoryImpl;
import util.Validator;
import util.exception.ValidateException;

import java.sql.Timestamp;
import java.util.UUID;

import static util.exception.ExceptionMessage.*;

public class UserAuthenticationService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final Validator validator;
    private static final int EXPIRATION_TIME_SESSION = 6;
    private static final int EXPIRATION_TIME_COOKIE = EXPIRATION_TIME_SESSION * 60 * 60;

    public UserAuthenticationService(UserRepository userRepository, SessionRepository sessionRepository, Validator validator) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.validator = validator;
    }

    public UserAuthenticationService() {
        this.userRepository = new UserRepositoryImpl();
        this.sessionRepository = new SessionRepositoryImpl();
        this.validator = new Validator();
    }

    public void registerUser(HttpServletRequest req) throws ValidateException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat-password");

        if (validator.checkValidityOfLogin(login)) throw new ValidateException(NO_LOGIN.message);
        if (validator.checkExistenceOfLogin(login)) throw new ValidateException(NO_UNIQUE_LOGIN.message);
        if (!validator.checkPasswordLength(password)) throw new ValidateException(SHORT_PASSWORD.message);
        if (!validator.checkIdentifyOfPasswords(password, repeatPassword))
            throw new ValidateException(NO_MATCH_PASSWORDS.message);

        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        User user = new User(login, bcryptHashString);

        userRepository.addNewUser(user);
    }

    public boolean verifyUserData(HttpServletRequest req) throws ValidateException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (validator.checkValidityOfLogin(login)) throw new ValidateException(NO_LOGIN.message);
        if (!validator.checkExistenceOfLogin(login)) throw new ValidateException(NO_SUCH_LOGIN.message);
        if (!validator.checkPasswordLength(password)) throw new ValidateException(SHORT_PASSWORD.message);

        String passwordHash = userRepository.getPasswordHash(login);
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);

        if (!result.verified) throw new ValidateException(WRONG_PASSWORD.message);

        return true;
    }

    public void loginUser(HttpServletRequest req, HttpServletResponse resp) {
        String uuid = UUID.randomUUID().toString();

        User user = null;
        if (userRepository.getUserByLogin(req.getParameter("login")).isPresent()) {
            user = userRepository.getUserByLogin(req.getParameter("login")).get();
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setHours(timestamp.getHours() + EXPIRATION_TIME_SESSION);

        Session session = new Session(uuid, user, timestamp);
        sessionRepository.addNewSession(session);

        Cookie cookie = new Cookie("user_id", uuid);
        cookie.setMaxAge(EXPIRATION_TIME_COOKIE);
        resp.addCookie(cookie);
    }

    public void logoutUser(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        String cookieName = "user_id";
        Cookie cookie = null;

        for (Cookie c : cookies) {
            if (cookieName.equals(c.getName())) {
                cookie = c;
                break;
            }
        }

        assert cookie != null;
        String uuid = cookie.getValue();

        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        sessionRepository.deleteSession(uuid);
    }
}
