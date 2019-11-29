package lvat.login01.controller.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lvat.login01.entity.User;
import lvat.login01.payload.LoginRequest;
import lvat.login01.payload.NewUserRequest;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "")
public class UserController extends TestController {
    public UserController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @RequestMapping(path = "/user/get/byEmail", method = RequestMethod.GET)
    public User getUserByEmail(@RequestParam(value = "email", defaultValue = "LVAT01@GMAIL.COM") String email) {
        return userService.findByEmailIs(email).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/get/byUsername", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam(value = "username", defaultValue = "lvat01") String email) {
        return userService.findByUsernameIs(email).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/get/byUsernameOrEmail", method = RequestMethod.GET)
    public User getUserByUsernameOrEmail(@RequestParam(value = "credentialString", defaultValue = "lvat01") String credentialString) {
        return userService.findByEmailIsOrUsername(credentialString, credentialString).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/user/post/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        LoggerFactory.getLogger(UserController.class).info("suckkkker");
        if (userService.existsByEmailIsOrUsernameIs(newUserRequest.getEmail(), newUserRequest.getUsername())) {
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

    @RequestMapping(path = "/user/post/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> login(@RequestBody LoginRequest loginRequest) {
        Logger logger = LoggerFactory.getLogger(UserController.class);
        logger.info(loginRequest.getPassword());
        logger.info(loginRequest.getUsernameOrEmail());

        return userService.findByPasswordIsAndUsernameIsOrPasswordIsAndEmailIs(loginRequest.getPassword(), loginRequest.getUsernameOrEmail());
    }

    @RequestMapping(path = "/user/get/jwt1", method = RequestMethod.GET)
    public String jwt1() {
        Logger logger = LoggerFactory.getLogger(UserController.class);
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "lvat")
                .setIssuer("leviathan00-issuer")
                .setSubject("leviathan-subject")
                .setAudience("leviathan00-audience")
                .setExpiration(new Date(2021, Calendar.DECEMBER, 20))
                .setNotBefore(new Date(2022, Calendar.DECEMBER, 20))
                .setIssuedAt(new Date(2023, Calendar.DECEMBER, 20))
                .setId("leviathan00-jti")
                .compact();
        logger.info(jwt);
        return jwt;
    }

    @RequestMapping(path = "/user/get/jwt2", method = RequestMethod.GET)
    public String jwt2(@RequestParam(value = "jwt") String jwt, @RequestParam(value = "claims") String claims) {
        return (String) Jwts.parser()
                .setSigningKey("lvat")
                .parseClaimsJws(jwt).getBody()
                .get(claims);
    }
}
