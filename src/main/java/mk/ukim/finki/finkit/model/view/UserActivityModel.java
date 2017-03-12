package mk.ukim.finki.finkit.model.view;

import java.util.Date;

import mk.ukim.finki.finkit.util.DateFormatterUtils;

public class UserActivityModel implements Comparable<UserActivityModel> {

	private Long postId;
	
	private String postName;
	
	private UserActivityType activityType;
	
	private String postComment;
	
	private Date timestamp;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public UserActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(UserActivityType activityType) {
		this.activityType = activityType;
	}

	public String getPostComment() {
		return postComment;
	}

	public void setPostComment(String postComment) {
		this.postComment = postComment;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getFormattedTimestamp() {
		return DateFormatterUtils.formatDate(this.timestamp);
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(UserActivityModel userActivityModel) {
		return userActivityModel.getTimestamp().compareTo(this.getTimestamp());
	}
	
}
