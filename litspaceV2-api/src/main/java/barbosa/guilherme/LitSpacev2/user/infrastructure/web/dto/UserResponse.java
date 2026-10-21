package barbosa.guilherme.LitSpacev2.user.infrastructure.web.dto;


import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;

public record UserResponse(
        Long id,
        String name,
        String email,
        String username
) {

    public static UserResponse fromOutput(UserOutput output) {
        return new UserResponse(
                output.id(),
                output.name(),
                output.email(),
                output.username()
        );
    }
}