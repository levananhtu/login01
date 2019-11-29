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

    public Optional<User> findByEmailIsOrUsername(String email, String username) {
        return userRepository.findByEmailIsOrUsername(email, username);
    }

    public Optional<User> findByEmailIs(String email) {
        return userRepository.findByEmailIs(email);
    }

    public Optional<User> findByUsernameIs(String username) {
        return userRepository.findByUsernameIs(username);
    }

    public Boolean existsByUsernameIs(String username) {
        return userRepository.existsByUsernameIs(username);
    }

    public Boolean existsByEmailIs(String email) {
        return userRepository.existsByEmailIs(email);
    }

    public Boolean existsByEmailIsOrUsernameIs(String email, String username) {
        return userRepository.existsByEmailIsOrUsernameIs(email, username);
    }

    public Boolean existsByEmailIsOrUsernameIs(String usernameOrEmail) {
        return userRepository.existsByEmailIsOrUsernameIs(usernameOrEmail, usernameOrEmail);
    }

    public Optional<User> findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(String password, String username, String email) {
        return userRepository.findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(password, username, password, email);
    }

    public Optional<User> findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(String password, String usernameOrEmail) {
        return userRepository.findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(password, usernameOrEmail, password, usernameOrEmail);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

}
