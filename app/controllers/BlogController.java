package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.PostService;
import services.UserService;
import views.html.blog;
import views.html.usersBlog;

import javax.inject.Inject;

/**
 * Blog Controller, with actions that return blog for username, and home page (list of posts of all users)
 *
 * @author Dusan
 */
public class BlogController extends Controller {

    private static final int N_OF_LATEST_POSTS = 5;
    private final PostService postService;
    private final UserService userService;

    @Inject
    public BlogController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    /**
     * Home page, list of all Posts by all users, ordered by create date, paginated.
     *
     * @param page - page index
     * @return blog template
     */
    public Result blog(int page) {
        return ok(blog.render(postService.findNLatestPosts(N_OF_LATEST_POSTS, page)));
    }

    /**
     * Blog of User with username, list of all Posts by that user, ordered by date of creation, paginated.
     *
     * @param username - username of User
     * @param page - page index
     * @return usersBlog template, or notFound if User with username doesn't exists
     */
    public Result usersBlog(String username, int page) {
        return userService.findUserByUsername(username)
                .map(userDTO ->
                        postService.findNLatestPostsForUsername(N_OF_LATEST_POSTS, page, username)
                                .map(postDTOs -> ok(usersBlog.render(userDTO, postDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

}
