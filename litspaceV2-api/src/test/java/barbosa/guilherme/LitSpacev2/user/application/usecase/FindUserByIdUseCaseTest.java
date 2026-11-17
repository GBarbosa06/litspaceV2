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
class FindUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @Test
    @DisplayName("Deve retornar UserOutput quando o usuário for encontrado pelo ID")
    void shouldReturnUserWhenIdExists() {
        // --- ARRANGE ---
        Long userId = 1L;
        var userEncontrado = new User(
                "Guilherme", "guilherme", "guilherme@email.com", "senha123"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEncontrado));

        // --- ACT ---
        UserOutput output = findUserByIdUseCase.execute(userId);

        // --- ASSERT ---
        assertNotNull(output);
        assertEquals("Guilherme", output.name());
        assertEquals("guilherme", output.username());
        assertEquals("guilherme@email.com", output.email());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o ID não existir")
    void shouldThrowExceptionWhenUserNotFound() {
        // --- ARRANGE ---
        Long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // --- ACT & ASSERT ---
        assertThrows(UserNotFoundException.class, () -> findUserByIdUseCase.execute(userId));

        verify(userRepository, times(1)).findById(userId);
    }
}