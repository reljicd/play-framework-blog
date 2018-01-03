package controllers;

import annotations.Authenticated;
import annotations.PostExistsAndUserIsOwner;
import dto.PostDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.CommentService;
import services.PostService;
import views.html.editPost;
import views.html.newPost;
import views.html.post;

import javax.inject.Inject;

/**
 * Controller with actions related to Posts.
 *
 * @author Dusan
 */
public class PostController extends Controller {

    private final PostService postService;
    private final CommentService commentService;
    private final Form<PostDTO> postForm;

    @Inject
    public PostController(PostService postService, CommentService commentService, FormFactory formFactory) {
        this.postService = postService;
        this.commentService = commentService;
        this.postForm = formFactory.form(PostDTO.class);
    }

    /**
     * GET Post page for post with id = postId.
     */
    public Result getPost(Long postId) {
        return commentService.findCommentsForPost(postId)
                .map(commentDTOs ->
                        postService.getPost(postId)
                                .map(postDTO -> ok(post.render(postDTO, commentDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

    /**
     * GET new Post form.
     * Only authenticated users can get this form.
     */
    @Authenticated
    public Result getNewPostForm() {
        return ok(newPost.render(postForm));
    }

    /**
     * GET edit Post form.
     * Only authenticated users and users who are the owners of post can get this form.
     */
    @Authenticated
    @PostExistsAndUserIsOwner
    public Result getEditPostForm(Long postId) {
        return postService.getPost(postId)
                .map(postDTO -> ok(editPost.render(postForm.fill(postDTO), postId)))
                .orElseGet(Results::notFound);
    }

    /**
     * POST new Post form.
     * Only authenticated users can post this form.
     */
    @Authenticated
    public Result createPost() {
        Form<PostDTO> postForm = this.postForm.bindFromRequest();
        if (postForm.hasErrors()) {
            return badRequest(newPost.render(postForm));
        } else {
            PostDTO postDTO = postForm.get();
            postDTO.username = session("username");
            postDTO = postService.savePost(postDTO);
            return redirect(routes.PostController.getPost(postDTO.id));
        }
    }

    /**
     * POST edit Post form.
     * Only authenticated users and users who are the owners of post can post this form.
     */
    @Authenticated
    @PostExistsAndUserIsOwner
    public Result editPost(Long postId) {
        Form<PostDTO> postForm = this.postForm.bindFromRequest();
        if (postForm.hasErrors()) {
            return badRequest(editPost.render(postForm, postId));
        } else {
            PostDTO postDTO = postForm.get();
            postDTO.id = postId;
            return postService.editPost(postDTO)
                    .map(x -> redirect(routes.PostController.getPost(postId)))
                    .orElseGet(Results::notFound);
        }
    }

    /**
     * DELETE Post.
     * Only authenticated users and users who are the owners of post can delete post.
     */
    @Authenticated
    @PostExistsAndUserIsOwner
    public Result deletePost(Long postId) {
        postService.delete(postId);
        return redirect(routes.BlogController.usersBlog(session("username"), 1));
    }

}
