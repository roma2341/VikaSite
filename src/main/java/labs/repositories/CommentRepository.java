package labs.repositories;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import labs.models.Comment;
import labs.models.Post;
import labs.models.User;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    ArrayList<Comment> findByAuthorId(Long authorId);
    ArrayList<Comment> findAll();
    ArrayList<Comment> findByPost(Post post);
}
