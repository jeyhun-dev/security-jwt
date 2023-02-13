package az.cmammad.security.exception;

public class PasswordIsWrongException extends RuntimeException {

    private static final String WRONG_PASSWORD = "User password is wrong, repeat correct password please...";

    public PasswordIsWrongException() {
        super(WRONG_PASSWORD);
    }
}
