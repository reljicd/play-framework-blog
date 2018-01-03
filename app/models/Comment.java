package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dusan
 */
@Entity
public class Comment extends Model {

    @Id
    public Long id;

    @Column(columnDefinition = "TEXT")
    public String body;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedTimestamp
    @Column(updatable = false)
    public Date createDate;

    @NotNull
    @ManyToOne
    public Post post;

    @NotNull
    @ManyToOne
    public User user;

    public static final Finder<Long, Comment> find = new Finder<>(Comment.class);

    public Comment(String body, Post post, User user) {
        this.body = body;
        this.post = post;
        this.user = user;
    }
}
