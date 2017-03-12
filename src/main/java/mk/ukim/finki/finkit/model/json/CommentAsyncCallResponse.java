package mk.ukim.finki.finkit.model.json;

import mk.ukim.finki.finkit.model.data.Comment;
import mk.ukim.finki.finkit.model.data.User;

public class CommentAsyncCallResponse {

	private User user;
	
	private Comment comment;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
