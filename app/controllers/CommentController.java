package controllers;

import annotations.Authenticated;
import dto.CommentDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.CommentService;
import views.html.comment;

import javax.inject.Inject;

public class CommentController extends Controller {

    private final CommentService commentService;
    private final Form<CommentDTO> commentForm;

    @Inject
    public CommentController(CommentService commentService, FormFactory formFactory) {
        this.commentService = commentService;
        this.commentForm = formFactory.form(CommentDTO.class);
    }

    @Authenticated
    public Result getCommentForm(Long postId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.postId = postId;
        return ok(comment.render(commentForm.fill(commentDTO), postId));
    }

    @Authenticated
    public Result createComment(Long postId) {
        Form<CommentDTO> commentForm = this.commentForm.bindFromRequest();
        if (commentForm.hasErrors()) {
            return badRequest(comment.render(commentForm, postId));
        } else {
            CommentDTO commentDTO = commentForm.get();
            commentDTO.username = session("username");
            commentDTO.postId = postId;
            commentService.saveComment(commentDTO);
            return redirect(routes.PostController.getPost(postId));
        }
    }
}
