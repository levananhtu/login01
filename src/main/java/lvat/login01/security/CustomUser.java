package lvat.login01.security;

import lvat.login01.entity.Role;
import lvat.login01.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomUser implements UserDetails, OAuth2User {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String name;

    private Boolean enabled;

    private Boolean expired;

    private Boolean locked;

    private List<GrantedAuthority> authorities = new LinkedList<>();

    private Map<String, Object> attributes;

    public CustomUser() {
    }

    public CustomUser(Integer id, String username, String email, String password, String name, Boolean enabled, Boolean expired, Boolean locked, List<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.expired = expired;
        this.locked = locked;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static CustomUser create(User user) {
        CustomUser customUser = new CustomUser();
        customUser.id = user.getId();
        customUser.username = user.getUsername();
        customUser.email = user.getEmail();
        customUser.password = user.getPassword();
        customUser.name = user.getName();
        customUser.enabled = user.getEnabled();
        customUser.expired = user.getExpired();
        customUser.locked = user.getLocked();
        customUser.authorities = new LinkedList<>();
        for (Role role : user.getRoleList()) {
            customUser.authorities.add(new SimpleGrantedAuthority(role.getName().getRoleName()));
        }
        customUser.attributes = null;
        return customUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return null;
    }
}
