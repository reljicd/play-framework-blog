import dto.LoginDTO;
import dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;
import services.UserService;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends WithApplication {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "testuser@testuser.com";

    private UserService userService;

    @Before
    public void setUp() {
        userService = app.injector().instanceOf(UserService.class);
    }

    @Test
    public void saveUserInDatabaseAndThenAuthenticateUser() {
        UserDTO userDTO = new UserDTO(USERNAME, PASSWORD, EMAIL, "firstName", "lastName");
        LoginDTO loginDTO = new LoginDTO(USERNAME, PASSWORD);

        userService.saveUser(userDTO);

        assertThat(userService.findUserByUsername(USERNAME).isPresent()).isTrue();
        assertThat(userService.findUserByEmail(EMAIL).isPresent()).isTrue();
        assertThat(userService.authenticateUser(loginDTO).isPresent()).isTrue();
    }
}
