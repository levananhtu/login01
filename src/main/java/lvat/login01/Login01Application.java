package lvat.login01;

import lvat.login01.entity.Role;
import lvat.login01.entity.User;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class Login01Application {

    public static void main(String[] args) {
        SpringApplication.run(Login01Application.class, args);
    }

    @Bean
    public CommandLineRunner demoData(RoleService roleService, UserService userService) {
        return args -> {
            try {
                roleService.save(new Role(Role.RoleName.ADMIN));
                roleService.save(new Role(Role.RoleName.ROOT));
                User user = new User("lvat01", "lvat01@gmail.com", "123456", "leviathan");
                user.setRoleList(Collections.singletonList(new Role(Role.RoleName.USER)));
                userService.save(user);
            } catch (Exception ignored) {

            }
        };
    }

}
