package barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.CreateUserInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "NAME IS REQUIRED")
        @Size(max = 150, message = "NAME CANNOT EXCEED 150 CHARACTERS")
        String name,

        @NotBlank(message = "EMAIL IS REQUIRED")
        @Email(message = "INVALID EMAIL FORMAT")
        @Size(max = 150, message = "EMAIL CANNOT EXCEED 150 CHARACTERS")
        String email,

        @NotBlank(message = "USERNAME IS REQUIRED")
        @Size(min = 3, max = 50, message = "USERNAME MUST BE BETWEEN 3 AND 50 CHARACTERS")
        String username,

        @NotBlank(message = "PASSWORD IS REQUIRED")
        @Size(min = 6, max = 100, message = "PASSWORD MUST HAVE AT LEAST 6 CHARACTERS")
        String password
) {
    public CreateUserInput toInput() {
        return new CreateUserInput(name, email, username, password);
    }
}
