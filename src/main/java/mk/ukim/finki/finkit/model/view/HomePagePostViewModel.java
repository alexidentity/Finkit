package mk.ukim.finki.finkit.model.view;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import mk.ukim.finki.finkit.model.data.Post;

public class HomePagePostViewModel implements Comparable<HomePagePostViewModel> {
	
	private Post post;
	
	private long upvotes;
	
	private long downvotes;
	
	private boolean isPostUpvotedByUser;
	
	private boolean isPostDownvotedByUser;
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public long getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(long upvotes) {
		this.upvotes = upvotes;
	}

	public long getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(long downvotes) {
		this.downvotes = downvotes;
	}
	
	public boolean getIsPostUpvotedByUser() {
		return isPostUpvotedByUser;
	}

	public void setPostUpvotedByUser(boolean isPostUpvotedByUser) {
		this.isPostUpvotedByUser = isPostUpvotedByUser;
	}

	public boolean getIsPostDownvotedByUser() {
		return isPostDownvotedByUser;
	}

	public void setPostDownvotedByUser(boolean isPostDownvotedByUser) {
		this.isPostDownvotedByUser = isPostDownvotedByUser;
	}

	@Override
	public int compareTo(HomePagePostViewModel o) {
		return Long.valueOf(o.upvotes - o.downvotes).compareTo(
				Long.valueOf(this.upvotes - this.downvotes));
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
