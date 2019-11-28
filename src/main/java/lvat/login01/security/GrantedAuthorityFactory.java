package lvat.login01.security;

import lvat.login01.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityFactory {
    private static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority(Role.RoleName.ADMIN.getRoleName());
    private static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority(Role.RoleName.USER.getRoleName());

    private GrantedAuthorityFactory() {
    }

    public static GrantedAuthority getAuthority(Role.RoleName roleName) {
        if (roleName.equals(Role.RoleName.ADMIN)) {
            return ADMIN_AUTHORITY;
        }
        if (roleName.equals(Role.RoleName.USER)) {
            return USER_AUTHORITY;
        }
        return null;
    }
}
