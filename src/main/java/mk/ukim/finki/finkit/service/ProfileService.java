package mk.ukim.finki.finkit.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.Comment;
import mk.ukim.finki.finkit.model.data.Post;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.data.UserFollow;
import mk.ukim.finki.finkit.model.data.UserPostVote;
import mk.ukim.finki.finkit.model.view.ProfileViewModel;
import mk.ukim.finki.finkit.model.view.UserActivityModel;
import mk.ukim.finki.finkit.model.view.UserActivityType;
import mk.ukim.finki.finkit.model.view.UserStatistics;
import mk.ukim.finki.finkit.repositories.CommentRepository;
import mk.ukim.finki.finkit.repositories.PostRepository;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.repositories.UserFollowRepository;
import mk.ukim.finki.finkit.repositories.UserPostVotesRepository;

@Service
public class ProfileService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFollowRepository userFollowRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserPostVotesRepository userPostVotesRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;
	
	@Autowired
	private VotingService votingService;
	
	@Autowired
	private TopicInfoRepository topicInfoRepository;
	
  public boolean followOrUnfollowUser(Long userId, Long followerId) {
    
    UserFollow userFollow = userFollowRepository.findByUserIdAndFollowerId(userId, followerId);
    boolean isUserFollowing;
    
    if (userFollow != null) {
    	userFollowRepository.delete(userFollow);
    	isUserFollowing = false;
    } else {
      UserFollow userToFollow = new UserFollow();
      userToFollow.setUserId(userId);
      userToFollow.setFollowerId(followerId);
      userFollowRepository.save(userToFollow);
      isUserFollowing = true;
    }
    
    return isUserFollowing;
  }
  
  public List<User> getFollowersForUser(Long userId) {
  	List<UserFollow> userFollowers = userFollowRepository.findByUserId(userId);
  	
  	List<User> followers = new ArrayList<User>();
  	for (UserFollow userFollow : userFollowers) {
			User follower = userService.getUserFromUserId(userFollow.getFollowerId());
			followers.add(follower);
		}
  	
		return followers;
  }
  
  public void getAndMapUserActivityAndStatistics(ProfileViewModel profileViewModel) {
  	
  	List<UserActivityModel> userActivityModelList = Lists.newArrayList();

  	UserStatistics userStatistics = new UserStatistics();
  	profileViewModel.setUserStatistics(userStatistics);
  	
  	User user = profileViewModel.getUser();
  	
  	fillUserVotes(user, userActivityModelList);
  	fillUserComments(user, userActivityModelList, userStatistics);
  	fillUserPosts(user, userActivityModelList, userStatistics);
  	fillUserTopics(user, profileViewModel, userStatistics);
  	
  	Collections.sort(userActivityModelList);
  	
		profileViewModel.setUserActivityModelList(
				userActivityModelList.stream().limit(10).collect(Collectors.toList())
		);
		
		long totalKarmaPoints = calculateTotalKarmaPoints(userStatistics);
		userStatistics.setTotalKarmaPoints(totalKarmaPoints);
		
  }

	private long calculateTotalKarmaPoints(UserStatistics userStatistics) {

		long numberOfPosts = userStatistics.getNumberOfPosts();
		long numberOfComments = userStatistics.getNumberOfComments();
		long numberOfVotesOnPosts = userStatistics.getNumberOfVotesOnPosts();
		long numberOfSubscribersOnTopics = userStatistics.getNumberOfSubscribersOnTopics();
		
		BigDecimal factor = BigDecimal.valueOf(numberOfVotesOnPosts/100.0 + 1);
		BigDecimal postKarma = factor.multiply(BigDecimal.valueOf(numberOfPosts));
		
		BigDecimal totalKarmaPoints =
				BigDecimal.valueOf(numberOfComments)
					.add(BigDecimal.valueOf(numberOfSubscribersOnTopics * 1.2))
					.add(BigDecimal.valueOf(numberOfVotesOnPosts))
					.add(postKarma);
		
		return totalKarmaPoints.setScale(0, BigDecimal.ROUND_DOWN).longValue();
	}

	private void fillUserTopics(
			User user, 
			ProfileViewModel profileViewModel, 
			UserStatistics userStatistics) {
		
		List<String> createdTopics = new ArrayList<String>();
		
		List<TopicInfo> topicInfoList = topicInfoRepository.findByModerator(user.getLogin());
		for (TopicInfo topicInfo : topicInfoList) {
			createdTopics.add(topicInfo.getSubtopic());
			
			long numberOfSubscribers = userSubtopicSubscriptionsService.getNumberOfSubscribers(topicInfo.getId());
			userStatistics.sumNumberOfSubscribersOnTopics(numberOfSubscribers);
		}
		
		profileViewModel.setCreatedTopics(createdTopics);
		userStatistics.setNumberOfTopics(CollectionUtils.size(createdTopics));
	}

	private void fillUserPosts(
			User user, 
			List<UserActivityModel> userActivityModelList, 
			UserStatistics userStatistics) {

		List<Post> posts = postRepository.findByPostedBy(user.getLogin());
		userStatistics.setNumberOfPosts(CollectionUtils.size(posts));
		
  	for (Post post : posts) {
			
  		UserActivityModel userActivityModel = new UserActivityModel();
  		
  		Long postId = post.getId();
			
  		userActivityModel.setActivityType(UserActivityType.CREATED);
			userActivityModel.setPostComment(StringUtils.EMPTY);
			userActivityModel.setPostId(postId);
			userActivityModel.setPostName(post.getTitle());
			userActivityModel.setTimestamp(post.getPostInfo());
			userActivityModelList.add(userActivityModel);
			
			long upvotesForPost = votingService.getUpvotesForPost(postId);
			long downvotesForPost = votingService.getDownvotesForPost(postId);
			long sumOfVotesForPost = upvotesForPost - downvotesForPost;
			
			userStatistics.sumNumberOfVotesOnPosts(sumOfVotesForPost);
		}
	}

	private void fillUserComments(
			User user, 
			List<UserActivityModel> userActivityModelList, 
			UserStatistics userStatistics) {
		
		List<Comment> comments = commentRepository.findByUser(user);
		userStatistics.setNumberOfComments(CollectionUtils.size(comments));
		
  	for (Comment comment : comments) {
			
  		UserActivityModel userActivityModel = new UserActivityModel();
			
			userActivityModel.setActivityType(UserActivityType.COMMENTED);
			userActivityModel.setPostComment(comment.getContent());
			
			Long postId = comment.getPostId();
			userActivityModel.setPostId(postId);
			
			String postName = getPostNameByPostId(postId);
			userActivityModel.setPostName(postName);
			
			userActivityModel.setTimestamp(comment.getCreated());
			
			userActivityModelList.add(userActivityModel);
  	}
	}

	private String getPostNameByPostId(Long postId) {
		Post post = postRepository.getOne(postId);
		String postName = post.getTitle();
		return postName;
	}

	private void fillUserVotes(User user, List<UserActivityModel> userActivityModelList) {
		
		List<UserPostVote> userPostVotes = userPostVotesRepository.findByUserId(user.getId());
  	for (UserPostVote userPostVote : userPostVotes) {
  		
  		UserActivityModel userActivityModel = new UserActivityModel();
  		
  		VoteType voteType = userPostVote.getVoteType();
  		if (VoteType.UP.equals(voteType)) {
  			userActivityModel.setActivityType(UserActivityType.UPVOTED);
  		} else {
  			userActivityModel.setActivityType(UserActivityType.DOWNVOTED);
  		}
  
  		userActivityModel.setPostComment(StringUtils.EMPTY);
  		userActivityModel.setTimestamp(userPostVote.getVoteTimestamp());

  		Long postId = userPostVote.getPostId();
			userActivityModel.setPostId(postId);
  		
  		String postName = getPostNameByPostId(postId);
  		userActivityModel.setPostName(postName);
  		
  		userActivityModelList.add(userActivityModel);
		}
	}

	public ProfileViewModel getProfileViewModel(User user) {
		
		ProfileViewModel profileViewModel = new ProfileViewModel();
		profileViewModel.setUser(user);

		String username = user.getLogin();
		
		String loggedInUsername = userService.getUsernameFromLoggedInUser();
		if (username.equals(loggedInUsername)) {
			profileViewModel.setLoggedInUserOnHisProfile(true);
		} else {
			profileViewModel.setLoggedInUserOnHisProfile(false);
		}
		
		boolean isLoggedIn;
		if (loggedInUsername.isEmpty()) {
			isLoggedIn = false;
		} else {
			isLoggedIn = true;
		}
		
		List<User> followers = getFollowersForUser(user.getId());
		profileViewModel.setFollowers(followers);
		
		boolean isUserFollowing = false;
		if (isLoggedIn) {
			User loggedInUser = userService.getLoggedInUser();
			List<User> loggedInUserFollows = getFollowersForUser(loggedInUser.getId());
			
			for (User follower : loggedInUserFollows) {
				if (follower.getLogin().equals(username)) {
					isUserFollowing = true;
				}
			}
		}
		
		profileViewModel.setLoggedInUserFollowing(isUserFollowing);
		
		Long userId = userService.getUserIdFromLoggedInUser();
		List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(userId);
		profileViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
		
  	getAndMapUserActivityAndStatistics(profileViewModel);

		return profileViewModel;
	}
  
}
