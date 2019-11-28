package lvat.login01.controller.test;

import lvat.login01.entity.User;
import lvat.login01.payload.NewUserRequest;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "")
public class UserController extends TestController {
    public UserController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

//    @RequestMapping(path = "getUser", method = RequestMethod.GET)
//    public CustomUser getUser(@RequestParam(name = "id", defaultValue = "1") Integer id,
//                              @RequestParam(name = "email", defaultValue = "lvat01@gmail.com") String email,
//                              @RequestParam(name = "enabled", defaultValue = "true") Boolean enabled,
//                              @RequestParam(name = "expired", defaultValue = "false") Boolean expired,
//                              @RequestParam(name = "locked", defaultValue = "false") Boolean locked,
//                              @RequestParam(name = "name", defaultValue = "lvat") String name) {
//        return null;
//    }

    @RequestMapping(path = "/user/get/byEmail", method = RequestMethod.GET)
    public User getUserByEmail(@RequestParam(value = "email", defaultValue = "LVAT01@GMAIL.COM") String email) {
        return userService.findByEmailIgnoreCase(email).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/get/byUsername", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam(value = "username", defaultValue = "lvat01") String email) {
        return userService.findByUsernameEquals(email).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/get/byUsernameOrEmail", method = RequestMethod.GET)
    public User getUserByUsernameOrEmail(@RequestParam(value = "credentialString", defaultValue = "lvat01") String credentialString) {
        return userService.findByEmailIgnoreCaseOrUsernameEquals(credentialString, credentialString).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/post/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody NewUserRequest newUserRequest) {
        if (userService.existsByEmailIgnoreCaseOrUsernameEquals(newUserRequest.getEmail(), newUserRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Failure");
        }

        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setEnabled(newUserRequest.getEnabled());
        user.setLocked(newUserRequest.getLocked());
        user.setExpired(newUserRequest.getExpired());
        user.setName(newUserRequest.getName());
        user.setPassword(newUserRequest.getPassword());
        user.setRoleList(null);
        user.setUsername(newUserRequest.getUsername());
        try {
            user.setRoleList(roleService.getRoleListByRole(newUserRequest.getRole()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failure");
        }
        userService.save(user);

        return ResponseEntity.ok("Success");
    }
}
