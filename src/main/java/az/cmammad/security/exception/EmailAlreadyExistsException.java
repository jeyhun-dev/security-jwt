package az.cmammad.security.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String EMAIL_ALREADY_EXISTS = "User Email Already Exists";

    public EmailAlreadyExistsException() {
        super(EMAIL_ALREADY_EXISTS);
    }
}
