package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Model {

    @Id
    public Long id;

    @NotNull
    @Column(unique = true)
    public String username;

    @NotNull
    public String password;

    @NotNull
    @Column(unique = true)
    public String email;

    public String firstName;

    public String lastName;

    @NotNull
    public int active;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Role> roles;

    @OneToMany(mappedBy = "user")
    public List<Post> posts;

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public User(String username, String password, String email, String firstName, String lastName, int active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }
}
