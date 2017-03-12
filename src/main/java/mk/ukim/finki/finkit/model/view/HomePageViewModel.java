package mk.ukim.finki.finkit.model.view;

import java.util.List;

public class HomePageViewModel {
	
	private List<HomePagePostViewModel> posts;
	
	private TopicInfoSidebarViewModel topicInfoSidebar;
	
	private boolean isTopicStyleDefault;
	
	private List<String> subtopicSubscriptionList;
	
	public List<HomePagePostViewModel> getPosts() {
		return posts;
	}

	public void setPosts(List<HomePagePostViewModel> posts) {
		this.posts = posts;
	}

  public TopicInfoSidebarViewModel getTopicInfoSidebar() {
    return topicInfoSidebar;
  }

  public void setTopicInfoSidebar(TopicInfoSidebarViewModel topicInfoSidebar) {
    this.topicInfoSidebar = topicInfoSidebar;
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
