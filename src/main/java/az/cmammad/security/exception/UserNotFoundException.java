package az.cmammad.security.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND = "User Not Found";

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
