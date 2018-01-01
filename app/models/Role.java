package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role extends Model {

    @Id
    public Long id;

    @Column(unique = true)
    public String role;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    public List<User> users;

    public static final Finder<Long, Role> find = new Finder<>(Role.class);

    public Role(String role) {
        this.role = role;
    }
}
