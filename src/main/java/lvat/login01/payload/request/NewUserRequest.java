package lvat.login01.payload.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewUserRequest {
    @Length(max = 64, min = 4)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "username must contains only alphanumeric and underscores")
    //https://stackoverflow.com/questions/336210/regular-expression-for-alphanumeric-and-underscores
    private String username;

    @Email
    @NotBlank
    private String email;

    @Length(max = 64, min = 6)
    private String password;

    @Length(max = 64, min = 4)
    private String name;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean expired;

    @NotNull
    private Boolean locked;

    @NotNull
    private Integer role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
