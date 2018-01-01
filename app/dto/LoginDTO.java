package dto;

import play.data.validation.Constraints;

public class LoginDTO {

    @Constraints.Required(message = "*Please provide your username")
    public String username;

    @Constraints.Required(message = "*Please provide your password")
    public String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
