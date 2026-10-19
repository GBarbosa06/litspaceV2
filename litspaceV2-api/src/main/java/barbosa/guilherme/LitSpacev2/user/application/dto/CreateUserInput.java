package barbosa.guilherme.LitSpacev2.user.application.dto;

public record CreateUserInput(
        String name,
        String email,
        String username,
        String password
) {
}
