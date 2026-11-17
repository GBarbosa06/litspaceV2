package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.UpdatePasswordInput;
import barbosa.guilherme.LitSpacev2.user.application.port.PasswordHasher;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.InvalidPasswordException;
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
class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @Test
    @DisplayName("Deve atualizar a senha com sucesso quando o usuário existir e a senha atual estiver correta")
    void shouldUpdatePasswordSuccessfully() {
        // --- ARRANGE ---
        Long userId = 1L;
        var input = new UpdatePasswordInput(userId, "senhaAntiga", "novaSenha123");
        var user = new User("Guilherme", "guilherme", "guilherme@email.com", "senhaAntigaHasheada");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordHasher.matches("senhaAntiga", "senhaAntigaHasheada")).thenReturn(true);
        when(passwordHasher.hash("novaSenha123")).thenReturn("novaSenhaHasheada");

        // --- ACT ---
        assertDoesNotThrow(() -> updateUserPasswordUseCase.execute(input));

        // --- ASSERT ---
        assertEquals("novaSenhaHasheada", user.getPassword());
        verify(userRepository, times(1)).findById(userId);
        verify(passwordHasher, times(1)).matches("senhaAntiga", "senhaAntigaHasheada");
        verify(passwordHasher, times(1)).hash("novaSenha123");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não existir")
    void shouldThrowExceptionWhenUserNotFound() {
        // --- ARRANGE ---
        Long userId = 99L;
        var input = new UpdatePasswordInput(userId, "senhaAntiga", "novaSenha123");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // --- ACT & ASSERT ---
        assertThrows(UserNotFoundException.class, () -> updateUserPasswordUseCase.execute(input));

        verify(userRepository, times(1)).findById(userId);
        verify(passwordHasher, never()).matches(anyString(), anyString());
        verify(passwordHasher, never()).hash(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar InvalidPasswordException quando a senha atual estiver incorreta")
    void shouldThrowExceptionWhenCurrentPasswordIsIncorrect() {
        // --- ARRANGE ---
        Long userId = 1L;
        var input = new UpdatePasswordInput(userId, "senhaErrada", "novaSenha123");
        var user = new User("Guilherme", "guilherme", "guilherme@email.com", "senhaCorretaHasheada");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordHasher.matches("senhaErrada", "senhaCorretaHasheada")).thenReturn(false);

        // --- ACT & ASSERT ---
        InvalidPasswordException exception = assertThrows(
                InvalidPasswordException.class,
                () -> updateUserPasswordUseCase.execute(input)
        );

        assertEquals("CURRENT PASSWORD INCORRECT", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(passwordHasher, times(1)).matches("senhaErrada", "senhaCorretaHasheada");
        verify(passwordHasher, never()).hash(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
