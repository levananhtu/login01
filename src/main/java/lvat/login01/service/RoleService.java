package lvat.login01.service;

import lvat.login01.entity.Role;
import lvat.login01.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    public List<Role> getRoleListByRole(Integer role) {
        List<Role> roleList = new LinkedList<>();
        roleList.add(roleRepository.findByName(Role.RoleName.ADMIN).orElseThrow(RuntimeException::new));
        if (role == 1) {
            return roleList;
        }
        if (role == 2) {
            roleList.add(roleRepository.findByName(Role.RoleName.USER).orElseThrow(RuntimeException::new));
            return roleList;
        }
        throw new RuntimeException();
    }
}
