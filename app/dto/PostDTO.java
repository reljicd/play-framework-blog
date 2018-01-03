package dto;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

import java.util.Date;

/**
 * @author Dusan
 */
public class PostDTO {

    public Long id;

    @MinLength(value = 5, message = "*Your title must have at least 5 characters")
    @Required(message = "*Please provide title")
    public String title;

    @Required(message = "*Please provide body")
    public String body;

    public Date createDate;

    public String username;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String body, Date createDate, String username) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createDate = createDate;
        this.username = username;
    }
}
