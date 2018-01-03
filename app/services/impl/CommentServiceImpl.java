package services.impl;

import dto.CommentDTO;
import models.Comment;
import models.Post;
import models.User;
import services.CommentService;
import services.PostService;
import services.UserService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author Dusan
 */
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final PostService postService;

    @Inject
    public CommentServiceImpl(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public CommentDTO saveComment(CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        comment.save();
        return convertToDTO(comment);
    }

    @Override
    public Optional<List<CommentDTO>> findCommentsForPost(Long postId) {
        return Optional.ofNullable(Post.find.byId(postId))
                .map(post -> post.comments
                        .stream()
                        .map(this::convertToDTO)
                        .collect(toList()));
    }

    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(comment.body, comment.user.username, comment.post.id, comment.createDate);
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        User user = userService.findUserEntityByUsername(commentDTO.username).orElse(null);
        Post post = postService.getPostEntity(commentDTO.postId).orElse(null);
        return new Comment(commentDTO.body, post, user);
    }
}
