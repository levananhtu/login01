package lvat.login01.payload.response;

import lvat.login01.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtResponse {
    private String tokenType = "Bearer";
    private String accessToken = null;
    private String username = null;
    private String email = null;
    private List<GrantedAuthority> roles = null;


    public JwtResponse() {
    }

    public JwtResponse(String tokenType, String accessToken, String username, String email, List<GrantedAuthority> roles) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<GrantedAuthority> roles) {
        this.roles = roles;
    }
}
