package lvat.login01.security;

import lvat.login01.entity.User;
import lvat.login01.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmailIsOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Account with username or email " + username + "doesn't exist"));
        return CustomUser.create(user);
    }
}
