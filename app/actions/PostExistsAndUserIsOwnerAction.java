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
        Optional<PostDTO> optionalPost = postService.getPost(6L);
        if (!optionalPost.isPresent()) {
            return CompletableFuture.completedFuture(notFound());
        } else if (!optionalPost.get().username.equals(username)) {
            Result login = badRequest(views.html.login.render(loginDTOForm.withGlobalError("Please login with proper credentials to modify this post")));
            return CompletableFuture.completedFuture(login);
        } else {
            return delegate.call(ctx);
        }
    }

}
