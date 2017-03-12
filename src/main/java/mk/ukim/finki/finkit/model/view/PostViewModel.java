package mk.ukim.finki.finkit.model.view;

import java.util.List;

import mk.ukim.finki.finkit.model.data.Post;

public class PostViewModel {
	
	private Post post;
	
	private List<CommentViewModel> rootComments;
	
  private TopicInfoSidebarViewModel topicInfoSidebar;
  
  private List<String> subtopicSubscriptionList;
  
  private long upvotes;
  
  private long downvotes;
  
  private boolean isPostUpvotedByUser;
	
	private boolean isPostDownvotedByUser;
	
	private boolean isTopicStyleDefault;
  
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<CommentViewModel> getRootComments() {
		return rootComments;
	}

	public void setRootComments(List<CommentViewModel> rootComments) {
		this.rootComments = rootComments;
	}

  public TopicInfoSidebarViewModel getTopicInfoSidebar() {
    return topicInfoSidebar;
  }

  public void setTopicInfoSidebar(TopicInfoSidebarViewModel topicInfoSidebar) {
    this.topicInfoSidebar = topicInfoSidebar;
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
	
	public boolean getIsTopicStyleDefault() {
		return isTopicStyleDefault;
	}

	public void setIsTopicStyleDefault(boolean isTopicStyleDefault) {
		this.isTopicStyleDefault = isTopicStyleDefault;
	}

	public List<String> getSubtopicSubscriptionList() {
		return subtopicSubscriptionList;
	}

	public void setSubtopicSubscriptionList(List<String> subtopicSubscriptionList) {
		this.subtopicSubscriptionList = subtopicSubscriptionList;
	}
	
}
