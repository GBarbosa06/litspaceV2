package barbosa.guilherme.LitSpacev2.user.domain.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
    public InvalidPasswordException() {
        super("INVALID PASSWORD");
    }
}
