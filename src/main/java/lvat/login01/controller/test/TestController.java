package lvat.login01.controller.test;

import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    protected final UserService userService;
    protected final RoleService roleService;

    public TestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
}
