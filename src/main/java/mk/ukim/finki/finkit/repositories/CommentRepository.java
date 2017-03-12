package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.model.data.Comment;
import mk.ukim.finki.finkit.model.data.User;

public interface CommentRepository extends BaseRepository<Comment> {
	
	List<Comment> findByParentCommentIdIsNull();

  List<Comment> findByPostId(Long postId);
  
  List<Comment> findByUser(User user);
  
  List<Comment> findByPostIdAndParentCommentIdIsNull(Long postId);
	
}
