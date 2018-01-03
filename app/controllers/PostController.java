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

    public Result getPost(Long postId) {
        return commentService.findCommentsForPost(postId)
                .map(commentDTOs ->
                        postService.getPost(postId)
                                .map(postDTO -> ok(post.render(postDTO, commentDTOs)))
                                .orElseGet(Results::notFound))
                .orElseGet(Results::notFound);
    }

    @Authenticated
    public Result getNewPostForm() {
        return ok(newPost.render(postForm));
    }

    @Authenticated
    @PostExistsAndUserIsOwner
    public Result getEditPostForm(Long postId) {
        return postService.getPost(postId)
                .map(postDTO -> ok(editPost.render(postForm.fill(postDTO), postId)))
                .orElseGet(Results::notFound);
    }

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

    @Authenticated
    @PostExistsAndUserIsOwner
    public Result deletePost(Long postId) {
        postService.delete(postId);
        return redirect(routes.BlogController.usersBlog(session("username"), 1));
    }

}
