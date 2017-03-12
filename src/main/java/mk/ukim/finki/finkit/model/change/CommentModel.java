package mk.ukim.finki.finkit.model.change;

import java.util.Date;
import java.util.List;

import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.view.CommentViewModel;
import mk.ukim.finki.finkit.util.DateFormatterUtils;

public class CommentModel {
  
	private Long id;
	
	private String content;
	
	private Date created;

	private User user;
	
	private Long postId;
	
	private List<CommentViewModel> nestedComments;
	
	private Long parentCommentId;
	
	public Long getId() {
    return id;
	}
	
	public void setId(Long id) {
    this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}
	
	public String getCreatedFormatted() {
		return DateFormatterUtils.formatDate(getCreated());
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public List<CommentViewModel> getNestedComments() {
		return nestedComments;
	}

	public void setNestedComments(List<CommentViewModel> nestedComments) {
		this.nestedComments = nestedComments;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

}
