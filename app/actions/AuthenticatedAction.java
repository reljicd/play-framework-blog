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

public class AuthenticatedAction extends Action<Authenticated> {

    private final Form<LoginDTO> loginDTOForm;

    @Inject
    public AuthenticatedAction(FormFactory formFactory) {
        this.loginDTOForm = formFactory.form(LoginDTO.class);
    }

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        if (username == null) {
            Result login = badRequest(views.html.login.render(loginDTOForm.withGlobalError("Please login to see this page.")));
            return CompletableFuture.completedFuture(login);
        } else {
            return delegate.call(ctx);
        }
    }

}
