package actions;

import annotations.NotAuthenticated;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class NotAuthenticatedAction extends Action<NotAuthenticated> {

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        if (username != null) {
            Result login = redirect(controllers.routes.BlogController.usersBlog(username, 1));
            return CompletableFuture.completedFuture(login);
        } else {
            return delegate.call(ctx);
        }
    }

}
