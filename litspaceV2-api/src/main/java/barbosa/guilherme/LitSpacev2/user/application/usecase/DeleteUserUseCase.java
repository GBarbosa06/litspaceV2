package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("USER TO DELETE NOT FOUND")
        );
        userRepository.delete(user);
    }
}
