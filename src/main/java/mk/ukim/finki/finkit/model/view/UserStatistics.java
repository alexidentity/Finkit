package mk.ukim.finki.finkit.model.view;

public class UserStatistics {
	
	private long numberOfComments;
	
	private long numberOfPosts;
	
	private long numberOfTopics;
	
	private long numberOfVotesOnPosts;
	
	private long numberOfSubscribersOnTopics;
	
	private long totalKarmaPoints;

	public long getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(long numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public long getNumberOfPosts() {
		return numberOfPosts;
	}

	public void setNumberOfPosts(long numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	public long getNumberOfTopics() {
		return numberOfTopics;
	}

	public void setNumberOfTopics(long numberOfTopics) {
		this.numberOfTopics = numberOfTopics;
	}

	public long getNumberOfVotesOnPosts() {
		return numberOfVotesOnPosts;
	}

	public void setNumberOfVotesOnPosts(long numberOfVotesOnPosts) {
		this.numberOfVotesOnPosts = numberOfVotesOnPosts;
	}
	
	public void sumNumberOfVotesOnPosts(long sumOfVotesForPost) {
		this.numberOfVotesOnPosts += sumOfVotesForPost;
	}

	public long getNumberOfSubscribersOnTopics() {
		return numberOfSubscribersOnTopics;
	}

	public void setNumberOfSubscribersOnTopics(long numberOfSubscribersOnTopics) {
		this.numberOfSubscribersOnTopics = numberOfSubscribersOnTopics;
	}
	
	public void sumNumberOfSubscribersOnTopics(long numberOfSubscribers) {
		this.numberOfSubscribersOnTopics = numberOfSubscribers;
	}

	public long getTotalKarmaPoints() {
		return totalKarmaPoints;
	}

	public void setTotalKarmaPoints(long totalKarmaPoints) {
		this.totalKarmaPoints = totalKarmaPoints;
	}
	
}
