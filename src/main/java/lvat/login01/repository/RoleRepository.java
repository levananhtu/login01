package lvat.login01.repository;

import lvat.login01.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Role.RoleName roleName);

    Boolean existsByName(Role.RoleName roleName);
}
