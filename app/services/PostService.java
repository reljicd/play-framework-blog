package services;

import dto.PostDTO;
import models.Post;
import util.PostsPager;

import java.util.List;
import java.util.Optional;

/**
 * Service class for {@link models.Post} domain objects
 *
 * @author Dusan
 */
public interface PostService {

    PostsPager findNLatestPosts(int n, int page);

    Optional<PostsPager> findNLatestPostsForUsername(int n, int page, String username);

    Optional<PostDTO> getPost(Long postId);

    Optional<Post> getPostEntity(Long postId);

    PostDTO savePost(PostDTO postDTO);

    Optional<PostDTO> editPost(PostDTO postDTO);

    void delete(Long postId);
}
