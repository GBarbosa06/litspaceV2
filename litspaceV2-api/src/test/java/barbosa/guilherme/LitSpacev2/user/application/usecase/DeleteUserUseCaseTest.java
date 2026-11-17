package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    @DisplayName("Deve deletar o usuário com sucesso")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;
        var userEncontrado = new User(
                "Guilherme", "guilherme", "guilherme@email.com", "senha123"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEncontrado));

        deleteUserUseCase.execute(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(userEncontrado);

    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não existir")
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.execute(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }
}
