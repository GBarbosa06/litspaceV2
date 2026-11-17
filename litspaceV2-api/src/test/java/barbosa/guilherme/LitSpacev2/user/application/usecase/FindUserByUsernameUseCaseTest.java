package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByUsernameUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByUsernameUseCase findUserByUsernameUseCase;

    @Test
    @DisplayName("Deve retornar UserOutput quando o usuário for encontrado pelo username")
    void shouldReturnUserWhenUsernameExists() {
        // --- ARRANGE ---
        String username = "guilherme";
        var userEncontrado = new User(
                "Guilherme", username, "guilherme@email.com", "senha123"
        );

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEncontrado));

        // --- ACT ---
        UserOutput output = findUserByUsernameUseCase.execute(username);

        // --- ASSERT ---
        assertNotNull(output);
        assertEquals("Guilherme", output.name());
        assertEquals("guilherme", output.username());
        assertEquals("guilherme@email.com", output.email());

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o username não existir")
    void shouldThrowExceptionWhenUserNotFound() {
        // --- ARRANGE ---
        String username = "usuario_inexistente";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // --- ACT & ASSERT ---
        assertThrows(UserNotFoundException.class, () -> findUserByUsernameUseCase.execute(username));

        verify(userRepository, times(1)).findByUsername(username);
    }
}
