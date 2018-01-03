package util;

import dto.PostDTO;
import io.ebean.PagedList;
import models.Post;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Util class for Posts pagination.
 * Keeps reference to {@link PagedList} of Posts,
 * delegates some calls to it,
 * and has util methods that facilitate implementation of pagination on templates.
 *
 * @author Dusan
 */
public class PostsPager {

    private final PagedList<Post> postPagedList;

    public PostsPager(PagedList<Post> postPagedList) {
        this.postPagedList = postPagedList;
    }

    public List<PostDTO> getList() {
        return postPagedList.getList()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public int getPageIndex() {
        return postPagedList.getPageIndex() + 1;
    }

    public int getPageSize() {
        return postPagedList.getPageSize();
    }

    public boolean hasNext() {
        return getPageIndex() >= 1 && getPageIndex() < getTotalPageCount();
    }

    public boolean hasPrev() {
        return getPageIndex() > 1 && getPageIndex() <= getTotalPageCount();
    }

    public int getTotalPageCount() {
        return postPagedList.getTotalPageCount();
    }

    public int getTotalCount() {
        return postPagedList.getTotalCount();
    }

    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalCount();
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(post.id, post.title, post.body, post.createDate, post.user.username);
    }
}
