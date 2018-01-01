import com.google.inject.AbstractModule;
import services.CommentService;
import services.PostService;
import services.UserService;
import services.impl.CommentServiceImpl;
import services.impl.PostServiceImpl;
import services.impl.UserServiceImpl;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(CommentService.class).to(CommentServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
    }

}
