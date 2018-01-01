package services;


import dto.CommentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service class for {@link models.Comment} domain objects
 *
 * @author Dusan
 */
public interface CommentService {

    CommentDTO saveComment(CommentDTO commentDTO);

    Optional<List<CommentDTO>> findCommentsForPost(Long postId);

}
