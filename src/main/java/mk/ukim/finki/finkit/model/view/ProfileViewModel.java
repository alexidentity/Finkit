package mk.ukim.finki.finkit.model.view;

import java.util.List;

import mk.ukim.finki.finkit.model.data.User;

public class ProfileViewModel {
	
	private User user;
	
	private List<User> followers;
	
	private boolean isLoggedInUserOnHisProfile;
	
	private boolean isLoggedInUserFollowing;
	
	private List<String> subtopicSubscriptionList;
	
	private List<UserActivityModel> userActivityModelList;
	
	private UserStatistics userStatistics;
	
	private List<String> createdTopics;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	public boolean isLoggedInUserOnHisProfile() {
		return isLoggedInUserOnHisProfile;
	}

	public void setLoggedInUserOnHisProfile(boolean isLoggedInUserOnHisProfile) {
		this.isLoggedInUserOnHisProfile = isLoggedInUserOnHisProfile;
	}

	public boolean isLoggedInUserFollowing() {
		return isLoggedInUserFollowing;
	}

	public void setLoggedInUserFollowing(boolean isLoggedInUserFollowing) {
		this.isLoggedInUserFollowing = isLoggedInUserFollowing;
	}
	
	public List<String> getSubtopicSubscriptionList() {
		return subtopicSubscriptionList;
	}

	public void setSubtopicSubscriptionList(List<String> subtopicSubscriptionList) {
		this.subtopicSubscriptionList = subtopicSubscriptionList;
	}

	public List<UserActivityModel> getUserActivityModelList() {
		return userActivityModelList;
	}

	public void setUserActivityModelList(List<UserActivityModel> userActivityModelList) {
		this.userActivityModelList = userActivityModelList;
	}

	public UserStatistics getUserStatistics() {
		return userStatistics;
	}

	public void setUserStatistics(UserStatistics userStatistics) {
		this.userStatistics = userStatistics;
	}

	public List<String> getCreatedTopics() {
		return createdTopics;
	}

	public void setCreatedTopics(List<String> createdTopics) {
		this.createdTopics = createdTopics;
	}
	
}
