package lvat.login01.controller.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lvat.login01.entity.User;
import lvat.login01.security.non_oauth2.JwtProvider;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(path = "/user/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;


    public UserController(UserService userService, RoleService roleService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }


    @RequestMapping(path = "/get/byEmail", method = RequestMethod.GET)
    public User getUserByEmail(@RequestParam(value = "email", defaultValue = "LVAT01@GMAIL.COM") String email) {
        return userService.findByEmailIs(email).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/get/byUsername", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam(value = "username", defaultValue = "lvat01") String username) {
        return userService.findByUsernameIs(username).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/get/byUsernameOrEmail", method = RequestMethod.GET)
    public User getUserByUsernameOrEmail(@RequestParam(value = "credentialString", defaultValue = "lvat01") String credentialString) {
        return userService.findByEmailIsOrUsername(credentialString, credentialString).orElseThrow(RuntimeException::new);
    }

    @RequestMapping(path = "/get/jwt1", method = RequestMethod.GET)
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

    @RequestMapping(path = "/get/jwt2", method = RequestMethod.GET)
    public Object jwt2(@RequestParam(value = "jwt") String jwt, @RequestParam(value = "claims", defaultValue = "username") String claims) {
        return Jwts.parser()
                .setSigningKey(JwtProvider.getJwtAccessTokenSecret())
                .parseClaimsJws(jwt).getBody()
                .get(claims);
    }

}
