package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.CreateUserInput;
import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.port.PasswordHasher;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void shouldCreateUserSuccessfully(){

        var input = new CreateUserInput(
                "Guilherme", "guilherme@email.com", "guilherme", "senha123"
        );
        var userSalvoEsperado = new User(
                "Guilherme", "guilherme", "guilherme@email.com", "[TEMPORARY HASH]senha123"
        );

        // Ensinando mocks a responder
        when(userRepository.existsByEmail(input.email())).thenReturn(false);
        when(userRepository.existsByUsername(input.username())).thenReturn(false);
        when(passwordHasher.hash("senha123")).thenReturn("[TEMPORARY HASH]senha123");
        when(userRepository.save(any(User.class))).thenReturn(userSalvoEsperado);

        UserOutput output = createUserUseCase.execute(input);

        assertNotNull(output);
        assertEquals("Guilherme", output.name());
        assertEquals("guilherme@email.com", output.email());

        verify(userRepository, times(1)).existsByEmail(input.email());
        verify(userRepository, times(1)).existsByUsername(input.username());
        verify(passwordHasher, times(1)).hash("senha123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar UserAlreadyExistsException quando o e-mail já estiver cadastrado")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // --- ARRANGE ---
        var input = new CreateUserInput("Guilherme", "duplicado@email.com", "guilherme", "senha123");

        // Simulamos que o e-mail já existe no banco:
        when(userRepository.existsByEmail("duplicado@email.com")).thenReturn(true);

        // --- ACT & ASSERT ---
        // Afirma que a execução do lambda dispara a exceção esperada
        assertThrows(UserAlreadyExistsException.class, () -> createUserUseCase.execute(input));

        // Garante que o fluxo parou imediatamente e NUNCA tentou salvar no banco
        verify(userRepository, never()).save(any(User.class));
        verify(passwordHasher, never()).hash(anyString());
    }

    @Test
    @DisplayName("Deve lançar UserAlreadyExistsException quando o username já estiver cadastrado")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // --- ARRANGE ---
        var input = new CreateUserInput(
                "Guilherme", "guilherme@email.com", "username_duplicado", "senha123"
        );

        when(userRepository.existsByEmail("guilherme@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("username_duplicado")).thenReturn(true);

        // --- ACT & ASSERT ---
        assertThrows(UserAlreadyExistsException.class, () -> createUserUseCase.execute(input));

        verify(userRepository, never()).save(any(User.class));
        verify(passwordHasher, never()).hash(anyString());
    }
}
