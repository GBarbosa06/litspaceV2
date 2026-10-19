package barbosa.guilherme.LitSpacev2.user.application.dto.out;

import barbosa.guilherme.LitSpacev2.user.domain.User;

import java.time.Instant;

public record UserOutput(
        Long id,
        String name,
        String username,
        String email,
        Instant createdAt
) {
    public static UserOutput fromDomain(User user) {
        return new UserOutput(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt().toInstant()
        );
    }
}
