package dto;

import play.data.validation.Constraints.Required;

import java.util.Date;

/**
 * @author Dusan
 */
public class CommentDTO {

    @Required(message = "*Please write something")
    public String body;

    public String username;

    public Long postId;

    public Date createDate;

    public CommentDTO() {
    }

    public CommentDTO(String body, String username, Long postId, Date createDate) {
        this.body = body;
        this.username = username;
        this.postId = postId;
        this.createDate = createDate;
    }
}
