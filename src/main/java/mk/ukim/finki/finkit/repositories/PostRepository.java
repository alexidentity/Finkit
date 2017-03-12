package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.model.data.Post;

public interface PostRepository extends BaseRepository<Post> {
	
	List<Post> findBySubtopic(String subtopic);
	
	List<Post> findBySubtopicInOrderByPostInfoDesc(List<String> subtopics);
	
	List<Post> findByPostedBy(String postedBy);
	
}
