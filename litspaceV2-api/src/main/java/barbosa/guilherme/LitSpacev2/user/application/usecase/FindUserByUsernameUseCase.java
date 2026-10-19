package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindUserByUsernameUseCase {
    private final UserRepository userRepository;

    public FindUserByUsernameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutput execute(String username){
        User userFound = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return UserOutput.fromDomain(userFound);
    }
}
