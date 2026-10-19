package barbosa.guilherme.LitSpacev2.user.application.port;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String currentPassword, String encodedPassword);
}
