package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.PostService;
import services.UserService;
import views.html.blog;
import views.html.usersBlog;

import javax.inject.Inject;

public class BlogController extends Controller {

    private static final int N_OF_LATEST_POSTS = 5;
    private final PostService postService;
    private final UserService userService;

    @Inject
    public BlogController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    public Result blog(int page) {
        return ok(blog.render(postService.findNLatestPosts(N_OF_LATEST_POSTS, page)));
    }

    public Result usersBlog(String username, int page) {
        return userService.findUserByUsername(username)
                .map(userDTO ->
                        postService.findNLatestPostsForUsername(N_OF_LATEST_POSTS, page, username)
                                .map(postDTOs -> ok(usersBlog.render(userDTO, postDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

}
