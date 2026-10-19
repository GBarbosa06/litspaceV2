package barbosa.guilherme.LitSpacev2.user.infrastructure.persistence;

import barbosa.guilherme.LitSpacev2.user.application.port.UserRepository;
import barbosa.guilherme.LitSpacev2.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SpringUserRepository springUserRepository;

    public UserRepositoryImpl(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public User save(User user) {
        return springUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return springUserRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springUserRepository.findByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return springUserRepository.existsByUsername(username);
    }
}


