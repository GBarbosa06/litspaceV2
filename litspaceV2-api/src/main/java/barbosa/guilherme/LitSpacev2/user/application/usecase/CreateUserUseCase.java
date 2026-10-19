package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.port.PasswordHasher;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.application.dto.in.CreateUserInput;
import barbosa.guilherme.LitSpacev2.user.application.dto.out.UserOutput;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public CreateUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public UserOutput execute(CreateUserInput input){
        if(userRepository.existsByEmail(input.email())){
            throw new UserAlreadyExistsException("EMAIL ALREADY USED");
        }
        if(userRepository.existsByUsername(input.username())){
            throw new UserAlreadyExistsException("USERNAME ALREADY USED");
        }

        String hashedPassword = passwordHasher.hash(input.password());
        User newUser = new User(input.name(), input.username(), input.email(), input.password());
        userRepository.save(newUser);

        return UserOutput.fromDomain(newUser);
    }
}
