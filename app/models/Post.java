package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Dusan
 */
@Entity
public class Post extends Model {

    @Id
    public Long id;

    @NotNull
    public String title;

    @Column(columnDefinition = "TEXT")
    public String body;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedTimestamp
    @Column(updatable = false)
    public Date createDate;

    @NotNull
    @ManyToOne
    public User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    public List<Comment> comments;

    public static final Finder<Long, Post> find = new Finder<>(Post.class);

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

}
