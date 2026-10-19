package barbosa.guilherme.LitSpacev2.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Table(name = "users")
public class User {
    // implementar UserDetails quando usar security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER_ROLE;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    public void updatePassword(String newHashedPassword) {
        this.password = newHashedPassword;
        this.updatedAt = OffsetDateTime.from(Instant.now());
    }
}
