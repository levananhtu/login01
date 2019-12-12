package lvat.login01;

import lvat.login01.entity.Role;
import lvat.login01.entity.User;
import lvat.login01.property.AppProperties;
import lvat.login01.service.RoleService;
import lvat.login01.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties(value = AppProperties.class)
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
                User user01 = new User("lvat01", "lvat01@gmail.com", "123456", "leviathan");
                user01.setRoles(Collections.singleton(new Role(Role.RoleName.USER)));
                userService.save(user01);
                User user02 = new User("lvat02", "lvat02@gmail.com", "123456", "leviathan");
                user02.setRoles(Collections.singleton(new Role(Role.RoleName.USER)));

            } catch (Exception ignored) {

            }
        };
    }

}
