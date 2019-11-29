package lvat.login01.repository;

import lvat.login01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIsOrUsername(String email, String username);

    Optional<User> findByEmailIs(String email);

    Optional<User> findByUsernameIs(String username);

    Optional<User> findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(String password01, String username, String password02, String email);

//    Optional<User> findByPasswordIsAndUsernameIs()

    Boolean existsByUsernameIs(String username);

    Boolean existsByEmailIs(String email);

    Boolean existsByEmailIsOrUsernameIs(String email, String username);


}
