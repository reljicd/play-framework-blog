package actions;

import annotations.Authenticated;
import dto.LoginDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Action that checks if User is authenticated
 *
 * @author Dusan
 */
public class AuthenticatedAction extends Action<Authenticated> {

    private final Form<LoginDTO> loginDTOForm;

    @Inject
    public AuthenticatedAction(FormFactory formFactory) {
        this.loginDTOForm = formFactory.form(LoginDTO.class);
    }

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        if (username == null) {
            // User is not authenticated, show him Login form
            Result login = unauthorized(views.html.login.render(loginDTOForm.withGlobalError("Please login to see this page.")));
            return CompletableFuture.completedFuture(login);
        } else {
            // User is authenticated, call delegate
            return delegate.call(ctx);
        }
    }

}
