package services.impl;

import dto.PostDTO;
import models.Post;
import models.User;
import services.PostService;
import services.UserService;
import util.PostsPager;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author Dusan
 */
public class PostServiceImpl implements PostService {

    private final UserService userService;

    @Inject
    public PostServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public PostsPager findNLatestPosts(int n, int page) {
        return new PostsPager(Post.find.query().orderBy("create_date desc").setFirstRow(n * (page - 1)).setMaxRows(n).findPagedList());
    }

    @Override
    public Optional<PostsPager> findNLatestPostsForUsername(int n, int page, String username) {
        return User.find.query().where().eq("username", username).findOneOrEmpty()
                .map(user -> new PostsPager(
                        Post.find.query()
                                .where().eq("user_id", user.id)
                                .orderBy("create_date desc")
                                .setFirstRow(n * (page - 1))
                                .setMaxRows(n)
                                .findPagedList()));
    }

    @Override
    public Optional<PostDTO> getPost(Long postId) {
        return getPostEntity(postId)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<Post> getPostEntity(Long postId) {
        return Optional.ofNullable(Post.find.byId(postId));
    }

    @Override
    public PostDTO savePost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        post.save();
        return convertToDTO(post);
    }

    @Override
    public Optional<PostDTO> editPost(PostDTO postDTO) {
        return getPostEntity(postDTO.id)
                .map(post -> {
                    post.body = postDTO.body;
                    post.title = postDTO.title;
                    post.save();
                    return convertToDTO(post);
                });
    }

    @Override
    public void delete(Long postId) {
        Post.find.deleteById(postId);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(post.id, post.title, post.body, post.createDate, post.user.username);
    }

    private Post convertToEntity(PostDTO postDTO) {
        User user = userService.findUserEntityByUsername(postDTO.username).orElse(null);
        return new Post(postDTO.title, postDTO.body, user);
    }

}
