package lvat.login01.service;

import lvat.login01.entity.User;
import lvat.login01.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmailIgnoreCaseOrUsernameEquals(String email, String username) {
        return userRepository.findByEmailIgnoreCaseOrUsernameEquals(email, username);
    }

    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public Optional<User> findByUsernameEquals(String username) {
        return userRepository.findByUsernameEquals(username);
    }

    public Boolean existsByUsernameEquals(String username) {
        return userRepository.existsByUsernameEquals(username);
    }

    public Boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public Boolean existsByEmailIgnoreCaseOrUsernameEquals(String email, String username) {
        return userRepository.existsByEmailIgnoreCaseOrUsernameEquals(email, username);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

}
