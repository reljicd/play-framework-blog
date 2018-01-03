package actions;

import annotations.NotAuthenticated;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Action that checks if User is not authenticated
 *
 * @author Dusan
 */
public class NotAuthenticatedAction extends Action<NotAuthenticated> {

    public CompletionStage<Result> call(final Http.Context ctx) {
        String username = ctx.session().get("username");
        if (username != null) {
            // User is authenticated, redirect him to home page
            Result login = redirect(controllers.routes.BlogController.usersBlog(username, 1));
            return CompletableFuture.completedFuture(login);
        } else {
            // User is not authenticated, call delegate
            return delegate.call(ctx);
        }
    }

}
