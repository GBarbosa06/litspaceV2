package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    @Test
    @DisplayName("Deve retornar uma lista de UserOutput quando existirem usuários cadastrados")
    void shouldReturnListOfUsersWhenUsersExist() {
        // --- ARRANGE ---
        var user1 = new User("Guilherme", "guilherme", "guilherme@email.com", "senha123");
        var user2 = new User("Maria", "maria", "maria@email.com", "senha456");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // --- ACT ---
        List<UserOutput> output = listUsersUseCase.execute();

        // --- ASSERT ---
        assertNotNull(output);
        assertEquals(2, output.size());

        assertEquals("Guilherme", output.get(0).name());
        assertEquals("guilherme", output.get(0).username());
        assertEquals("guilherme@email.com", output.get(0).email());

        assertEquals("Maria", output.get(1).name());
        assertEquals("maria", output.get(1).username());
        assertEquals("maria@email.com", output.get(1).email());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não existirem usuários cadastrados")
    void shouldReturnEmptyListWhenNoUsersExist() {
        // --- ARRANGE ---
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // --- ACT ---
        List<UserOutput> output = listUsersUseCase.execute();

        // --- ASSERT ---
        assertNotNull(output);
        assertTrue(output.isEmpty());

        verify(userRepository, times(1)).findAll();
    }
}
