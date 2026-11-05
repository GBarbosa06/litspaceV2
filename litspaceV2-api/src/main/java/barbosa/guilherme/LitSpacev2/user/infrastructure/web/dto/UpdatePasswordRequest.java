package barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.UpdatePasswordInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "CURRENT PASSWORD REQUIRED")
        String currentPassword,

        @NotBlank(message = "NEW PASSWORD REQUIRED")
        @Size(min = 6, max = 100, message = "NEW PASSWORD MUST HAVE AT LEAST 6 CHARACTERS")
        String newPassword
) {
    public UpdatePasswordInput toInput(long id){
        return new UpdatePasswordInput(id, currentPassword, newPassword);
    }
}