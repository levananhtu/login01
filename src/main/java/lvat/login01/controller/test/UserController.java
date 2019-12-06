package lvat.login01.controller.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lvat.login01.entity.User;
import lvat.login01.payload.request.LoginRequest;
import lvat.login01.payload.request.NewTokenPairRequest;
import lvat.login01.payload.request.NewUserRequest;
import lvat.login01.payload.response.DetailTokenResponse;
import lvat.login01.payload.response.MessageResponse;
import lvat.login01.payload.response.TokenResponse;
import lvat.login01.security.CustomUser;
import lvat.login01.security.non_oauth2.JwtProvider;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

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

    /***/

    @RequestMapping(path = "/post/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        if (userService.existsByEmailIsOrUsernameIs(newUserRequest.getEmail(), newUserRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or email has already existed", HttpStatus.BAD_REQUEST));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Bad argument", HttpStatus.BAD_REQUEST));
        }
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("Account created successfully", HttpStatus.OK));
    }

    @RequestMapping(path = "/post/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Logger logger = LoggerFactory.getLogger(UserController.class);
        logger.info(loginRequest.getPassword());
        logger.info(loginRequest.getUsernameOrEmail());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Bad credential", HttpStatus.BAD_REQUEST));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtProvider.TokenPair tokenPair = jwtProvider.generateTokenPair(authentication);
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return ResponseEntity.ok(new DetailTokenResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken(), userDetails.getUsername(), userDetails.getEmail(), new LinkedList<>(userDetails.getAuthorities())));

    }

    @RequestMapping(path = "/post/getNewTokenPair", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNewTokenPair(@RequestBody NewTokenPairRequest request) {
        JwtProvider.TokenPair keyPair = jwtProvider.generateTokenPair(request.getRefreshToken());
        if (keyPair == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Token is not valid", HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(new TokenResponse(keyPair.getAccessToken(), keyPair.getRefreshToken()));
    }

    /***/

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
