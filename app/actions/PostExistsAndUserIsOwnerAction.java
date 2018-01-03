package actions;

import annotations.PostExistsAndUserIsOwner;
import dto.LoginDTO;
import dto.PostDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.PostService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Util Action that checks if Post exists and if logged in User is the owner of Post.
 * <p>
 * Returns notFound if Post doesn't exists or unauthorized if User is not the owner of Post.
 *
 * @author Dusan
 */
public class PostExistsAndUserIsOwnerAction extends Action<PostExistsAndUserIsOwner> {

    private final PostService postService;
    private final Form<LoginDTO> loginDTOForm;

    @Inject
    public PostExistsAndUserIsOwnerAction(PostService postService, FormFactory formFactory) {
        this.postService = postService;
        this.loginDTOForm = formFactory.form(LoginDTO.class);
    }

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        Long postId = Long.parseLong(ctx.request().getQueryString("id"));
        Optional<PostDTO> optionalPost = postService.getPost(postId);
        if (!optionalPost.isPresent()) {
            // Post doesn't exists
            return CompletableFuture.completedFuture(notFound());
        } else if (!optionalPost.get().username.equals(username)) {
            // User is not the owner of Post
            Result login = unauthorized(views.html.login.render(
                    loginDTOForm.withGlobalError("Please login with proper credentials to modify this post")));
            return CompletableFuture.completedFuture(login);
        } else {
            // Post exists and User is the owner of Post
            return delegate.call(ctx);
        }
    }

}
