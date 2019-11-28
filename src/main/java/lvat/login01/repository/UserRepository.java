package lvat.login01.repository;

import lvat.login01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCaseOrUsernameEquals(String email, String username);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsernameEquals(String username);

    Boolean existsByUsernameEquals(String username);

    Boolean existsByEmailIgnoreCase(String email);

    Boolean existsByEmailIgnoreCaseOrUsernameEquals(String email, String username);
}
