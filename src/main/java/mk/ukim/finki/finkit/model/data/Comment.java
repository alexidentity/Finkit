package mk.ukim.finki.finkit.model.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mk.ukim.finki.finkit.util.DateFormatterUtils;

@Entity
@Table(name="comments")
public class Comment {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "created")
	private Date created;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "post_id")
	private Long postId;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "comments", joinColumns = @JoinColumn(name = "parent_comment_id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Comment> nestedComments;
	
	@Column(name = "parent_comment_id")
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

	public List<Comment> getNestedComments() {
		return nestedComments;
	}

	public void setNestedComments(List<Comment> nestedComments) {
		this.nestedComments = nestedComments;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
	
}
