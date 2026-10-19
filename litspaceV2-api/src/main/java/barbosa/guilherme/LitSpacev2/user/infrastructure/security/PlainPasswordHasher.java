package barbosa.guilherme.LitSpacev2.user.infrastructure.security;

import barbosa.guilherme.LitSpacev2.user.application.port.PasswordHasher;
import org.springframework.stereotype.Component;

@Component
public class PlainPasswordHasher implements PasswordHasher {
    @Override
    public String hash(String rawPassword) {
        // Implementação temporária: apenas adiciona um prefixo fake ou retorna o texto plano
        return "[TEMPORARY HASH]" + rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword.replace("[TEMPORARY HASH]", ""));
    }
}
