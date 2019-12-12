package lvat.login01.service;

import lvat.login01.entity.Role;
import lvat.login01.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(Role.RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    public Boolean existsByName(Role.RoleName roleName) {
        return roleRepository.existsByName(roleName);
    }

    public Set<Role> getRoleSetByRole(Integer role) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByName(Role.RoleName.ADMIN).orElseThrow(RuntimeException::new));
        if (role == 1) {
            return roleSet;
        }
        if (role == 2) {
            roleSet.add(roleRepository.findByName(Role.RoleName.USER).orElseThrow(RuntimeException::new));
            return roleSet;
        }
        throw new RuntimeException();
    }

    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
