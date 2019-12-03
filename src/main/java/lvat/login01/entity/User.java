package lvat.login01.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, columnDefinition = "varbinary(255)")
    private String username;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "password", columnDefinition = "text")
    private String password;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "locked")
    private Boolean locked;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private List<Role> roleList = new LinkedList<>();

    public User() {
    }

    public User(String username, String email, String password, String name, Boolean enabled, Boolean expired, Boolean locked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.expired = expired;
        this.locked = locked;
    }

    public User(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = true;
        this.expired = false;
        this.locked = false;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Role> getRoleList() {
        return roleList;
    }

    public List<Role.RoleName> getRoleName() {
        List<Role.RoleName> list = new LinkedList<>();
        for (Role role : roleList) {
            list.add(role.getName());
        }
        return list;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
