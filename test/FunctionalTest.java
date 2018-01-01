import models.Role;
import org.junit.Ignore;
import org.junit.Test;
import play.test.WithApplication;
import play.twirl.api.Content;
import services.CommentService;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A functional test starts a Play application for every test.
 * <p>
 * https://www.playframework.com/documentation/latest/JavaFunctionalTest
 */
public class FunctionalTest extends WithApplication {

    @Test
    @Ignore
    public void renderTemplate() {
        // If you are calling out to Assets, then you must instantiate an application
        // because it makes use of assets metadata that is configured from
        // the application.

        Content html = views.html.index.render("Your new application is ready.");
        assertThat("text/html").isEqualTo(html.contentType());
        assertThat(html.body()).contains("Your new application is ready.");
    }

}
