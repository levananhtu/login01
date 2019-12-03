package lvat.login01;

import lvat.login01.entity.Role;
import lvat.login01.entity.User;
import lvat.login01.repository.RoleRepository;
import lvat.login01.repository.UserRepository;
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
    public CommandLineRunner demoData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            try {
                Role userRole = roleRepository.save(new Role(Role.RoleName.USER));
                roleRepository.save(new Role(Role.RoleName.ADMIN));
                roleRepository.save(new Role(Role.RoleName.ROOT));
                User user = new User("lvat01", "lvat01@gmail.com", "123456", "leviathan");
                user.setRoleList(Collections.singletonList(userRole));
                userRepository.save(user);
            } catch (Exception ignored) {

            }
        };
    }

}
