package lvat.login01.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lvat.login01.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sun.security.provider.DSAPrivateKey;
import sun.security.provider.DSAPublicKey;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

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
        Jwts.builder()
                .setIssuer("123456")
                .setSubject("asdfadsf")
                .setAudience("adsfasdf")
                .setExpiration(new Date())
                .setNotBefore(new Date())
                .setIssuedAt(new Date())
                .setId("zdfg");
//                .signWith(Si)
        PrivateKey privateKey = new DSAPrivateKey();
        PublicKey publicKey = new DSAPublicKey();
        return null;
    }
}
