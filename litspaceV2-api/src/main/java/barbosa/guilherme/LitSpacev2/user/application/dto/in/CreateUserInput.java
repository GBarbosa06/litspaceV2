package barbosa.guilherme.LitSpacev2.user.application.dto.in;

public record CreateUserInput(
        String name,
        String email,
        String username,
        String password
) {
}
