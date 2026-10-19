package barbosa.guilherme.LitSpacev2.user.application.port;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
