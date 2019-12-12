package lvat.login01.controller.test;

import lvat.login01.entity.Role;
import lvat.login01.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/get/byRole", method = RequestMethod.GET)
    public Object getByRoleName(@RequestParam(value = "role", defaultValue = "ADMIN") String role) {
        return roleService.findByName(Role.RoleName.valueOf(role.toUpperCase()));
    }
}
