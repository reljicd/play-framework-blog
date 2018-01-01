package dto;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class UserDTO {

    @MinLength(value = 5, message = "*Your username must have at least 5 characters")
    @Required(message = "*Please provide your username")
    public String username;

    @MinLength(value = 5, message = "*Your password must have at least 5 characters")
    @Required(message = "*Please provide your password")
    public String password;

    @Email(message = "*Please provide a valid Email")
    @Required(message = "*Please provide an email")
    public String email;

    @Required(message = "*Please provide your name")
    public String firstName;

    @Required(message = "*Please provide your last name")
    public String lastName;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
