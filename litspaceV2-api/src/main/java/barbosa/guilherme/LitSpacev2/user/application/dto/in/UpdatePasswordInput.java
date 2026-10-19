package barbosa.guilherme.LitSpacev2.user.application.dto.in;

public record UpdatePasswordInput(
        long id,
        String currentPassword,
        String newPassword
) {
}
