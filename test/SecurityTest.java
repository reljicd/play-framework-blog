import dto.PostDTO;
import dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import services.PostService;
import services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class SecurityTest extends WithApplication {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "testuser@testuser.com";

    private PostService postService;
    private UserService userService;

    @Before
    public void setUp() {
        userService = app.injector().instanceOf(UserService.class);
        postService = app.injector().instanceOf(PostService.class);

        UserDTO userDTO = new UserDTO(USERNAME, PASSWORD, EMAIL, "firstName", "lastName");
        userService.saveUser(userDTO);
    }

    @Test
    public void testUnauthenticatedUserRequestingProtectedPage() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri(controllers.routes.PostController.getNewPostForm().url());

        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void testAuthenticatedUserRequestingProtectedPage() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .session("username", USERNAME)
                .uri(controllers.routes.PostController.getNewPostForm().url());

        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void testAuthenticatedUserRequestingRegisterPage() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .session("username", USERNAME)
                .uri(controllers.routes.UserController.getRegistrationForm().url());

        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(SEE_OTHER);
    }

    @Test
    public void testAuthenticatedUserRequestingToEditHisPost() {
        PostDTO postDTO = postService.savePost(new PostDTO(null, "TEST", "TEST", null, USERNAME));
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .session("username", USERNAME)
                .uri(controllers.routes.PostController.getEditPostForm(postDTO.id).url());

        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void testAuthenticatedUserRequestingToEditPostByOtherUser() {
        UserDTO userDTO = new UserDTO("TEST", "TEST", "TEST@test.com", "firstName", "lastName");
        userService.saveUser(userDTO);
        PostDTO postDTO = postService.savePost(new PostDTO(null, "TEST", "TEST", null, userDTO.username));

        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .session("username", USERNAME)
                .uri(controllers.routes.PostController.getEditPostForm(postDTO.id).url());

        Result result = route(app, request);
        assertThat(result.status()).isEqualTo(UNAUTHORIZED);
    }
}

