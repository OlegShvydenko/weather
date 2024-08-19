package util.exception;

public enum ExceptionMessage {
    NO_LOGIN("Введите логин!"),
    NO_UNIQUE_LOGIN("Этот логин занят!"),
    NO_SUCH_LOGIN("Нет пользователя с таким логином!"),
    NO_MATCH_PASSWORDS("Пароли не совпадают!"),
    WRONG_PASSWORD("Неправильный пароль!"),
    SHORT_PASSWORD("Пароль должен содержать не менее 6 символов!");
    public final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
