package barbosa.guilherme.LitSpacev2.user.application.usecase;

import barbosa.guilherme.LitSpacev2.user.application.dto.in.UpdatePasswordInput;
import barbosa.guilherme.LitSpacev2.user.application.port.PasswordHasher;
import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.InvalidPasswordException;
import barbosa.guilherme.LitSpacev2.user.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserPasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UpdateUserPasswordUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public void execute(UpdatePasswordInput input){
        User user = userRepository.findById(input.id()).orElseThrow(UserNotFoundException::new);

        if(!passwordHasher.matches(user.getPassword(), input.newPassword())){
            throw new InvalidPasswordException("CURRENT PASSWORD INCORRECT");
        }

        String newHashedPassword = passwordHasher.hash(input.newPassword());
        user.updatePassword(newHashedPassword);
        userRepository.save(user);
    }
}
