import dto.PostDTO;
import dto.UserDTO;
import models.Post;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;
import services.PostService;
import services.UserService;
import util.PostsPager;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PostsPagerTest extends WithApplication {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "testuser@testuser.com";

    private static final int NUMBERS_OF_POSTS_PER_PAGE = 10;
    private static final int NUMBER_OF_DUMMY_POSTS = 20;

    private PostService postService;

    @Before
    public void setUp() {
        postService = app.injector().instanceOf(PostService.class);
        UserService userService = app.injector().instanceOf(UserService.class);

        UserDTO userDTO = new UserDTO(USERNAME, PASSWORD, EMAIL, "firstName", "lastName");
        userService.saveUser(userDTO);

        // Populate DB with some dummy posts

        IntStream.rangeClosed(1, NUMBER_OF_DUMMY_POSTS)
                .forEach(i -> postService.savePost(new PostDTO(null, "Title" + i, "body", null, USERNAME)));
    }

    @Test
    public void testGeneralFunctionsOfPostsPager() {
        int numberOfPosts = Post.find.all().size();
        int numberOfPages = numberOfPosts / NUMBERS_OF_POSTS_PER_PAGE + 1;
        int pageIndex = 1;
        PostsPager postsPager = postService.findNLatestPosts(NUMBERS_OF_POSTS_PER_PAGE, pageIndex);
        assertThat(postsPager.getPageSize()).isEqualTo(NUMBERS_OF_POSTS_PER_PAGE);
        assertThat(postsPager.getPageIndex()).isEqualTo(pageIndex);
        assertThat(postsPager.getTotalPageCount()).isEqualTo(numberOfPages);
        assertThat(postsPager.getTotalCount()).isEqualTo(numberOfPosts);
    }

    @Test
    public void testPostsPagerFirstPage() {
        int pageIndex = 1;
        PostsPager postsPager = postService.findNLatestPosts(NUMBERS_OF_POSTS_PER_PAGE, pageIndex);
        assertThat(postsPager.hasPrev()).isFalse();
        assertThat(postsPager.hasNext()).isTrue();
        assertThat(postsPager.indexOutOfBounds()).isFalse();
    }

    @Test
    public void testPostsPagerLastPage() {
        int pageIndex = 1;
        PostsPager postsPager = postService.findNLatestPosts(NUMBERS_OF_POSTS_PER_PAGE, pageIndex);
        postsPager = postService.findNLatestPosts(NUMBERS_OF_POSTS_PER_PAGE, postsPager.getTotalPageCount());
        assertThat(postsPager.hasPrev()).isTrue();
        assertThat(postsPager.hasNext()).isFalse();
        assertThat(postsPager.indexOutOfBounds()).isFalse();
    }

    @Test
    public void testPostsPagerPositiveOutOfBoundsPage() {
        testOutOfBoundsPage(200);
    }

    @Test
    public void testPostsPagerNegativeOutOfBoundsPage() {
        testOutOfBoundsPage(-200);
    }

    private void testOutOfBoundsPage(int pageIndex) {
        PostsPager postsPager = postService.findNLatestPosts(NUMBERS_OF_POSTS_PER_PAGE, pageIndex);
        assertThat(postsPager.hasPrev()).isFalse();
        assertThat(postsPager.hasNext()).isFalse();
        assertThat(postsPager.indexOutOfBounds()).isTrue();
    }

}
