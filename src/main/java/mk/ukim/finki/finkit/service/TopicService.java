package mk.ukim.finki.finkit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.ukim.finki.finkit.mapper.TopicMapper;
import mk.ukim.finki.finkit.model.change.TopicChangeModel;
import mk.ukim.finki.finkit.model.data.Post;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.view.HomePagePostViewModel;
import mk.ukim.finki.finkit.model.view.HomePageViewModel;
import mk.ukim.finki.finkit.model.view.TopicInfoSidebarViewModel;
import mk.ukim.finki.finkit.repositories.PostDao;
import mk.ukim.finki.finkit.repositories.PostRepository;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.repositories.UserRepository;
import mk.ukim.finki.finkit.util.CssRulesBuilder;

@Service
public class TopicService {
	
	private static final List<String> DEFAULT_TOPIC_SUBSCRIPTIONS =
			Arrays.asList("music", "news", "technology", "funny");
	
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private TopicInfoRepository topicInfoRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private VotingService votingService;
  
  @Autowired
  private TopicMapper topicMapper;
  
  @Autowired
  private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;
  
  @Autowired
  private PostDao postDao;
  
  public HomePageViewModel getHomepageViewModel(String subtopic, String searchQuery) {
  	List<HomePagePostViewModel> homePagePostViewModelList = new ArrayList<>();
  	
  	HomePageViewModel homePageViewModel = new HomePageViewModel();
    
    TopicInfoSidebarViewModel topicInfoSidebar = null;
    TopicInfo topicInfo = topicInfoRepository.findOneBySubtopic(subtopic);
    
    if (topicInfo != null) {
    	String moderator = topicInfo.getModerator();
    	User moderatorUser = userRepository.findUserByLogin(moderator);
    	
    	String navbarBackgroundColour = topicInfo.getNavbarBackgroundColour();
    	if (navbarBackgroundColour.equals("DEFAULT")) {
    		homePageViewModel.setIsTopicStyleDefault(true);
    	} else {
      	homePageViewModel.setIsTopicStyleDefault(false);
    	}
    	
    	topicInfoSidebar = topicMapper.mapTopicInfoToTopicInfoSidebarViewModel(topicInfo, moderatorUser);
    } else {
    	return null;
    }
    
    homePageViewModel.setTopicInfoSidebar(topicInfoSidebar);
  	
  	Long userId = userService.getUserIdFromLoggedInUser();
  	
    List<Post> posts;
    if (subtopic.equals("all")) {
    	if (userId != null) {
    		List<String> userSubscriptions = userSubtopicSubscriptionsService.getUserSubscriptions(userId);
    		posts = postRepository.findBySubtopicInOrderByPostInfoDesc(userSubscriptions);
    	} else {
    		List<Long> relevantPostIds = postDao.searchPostsBySubtopic(DEFAULT_TOPIC_SUBSCRIPTIONS);
    		posts = postRepository.findAll(relevantPostIds);
    		//posts = postRepository.findBySubtopicInOrderByPostInfoDesc(DEFAULT_TOPIC_SUBSCRIPTIONS);
    	}
    } else {
    	if (searchQuery.isEmpty()) {
    		posts = postRepository.findBySubtopic(subtopic);
    	} else {
    		List<Long> postIds = postDao.searchPostsByTitle(subtopic, searchQuery);
    		posts = postRepository.findAll(postIds);
    	}
    }
    
    for (Post post : posts) {
    	Long postId = post.getId();
			long upvotes = votingService.getUpvotesForPost(postId);
    	long downvotes = votingService.getDownvotesForPost(postId);
    	
    	HomePagePostViewModel homePagePostViewModel = new HomePagePostViewModel();
    	
  		if (userId != null) {
  			boolean isPostUpvotedByUser = votingService.isPostUpvotedByUser(postId, userId);
  			homePagePostViewModel.setPostUpvotedByUser(isPostUpvotedByUser);
  	    
  	    boolean isPostDownvotedByUser = votingService.isPostDownvotedByUser(postId, userId);
  	    homePagePostViewModel.setPostDownvotedByUser(isPostDownvotedByUser);
  		} else {
  			homePagePostViewModel.setPostUpvotedByUser(false);
  			homePagePostViewModel.setPostDownvotedByUser(false);
  		}
    	
    	homePagePostViewModel.setPost(post);
    	homePagePostViewModel.setUpvotes(upvotes);
    	homePagePostViewModel.setDownvotes(downvotes);
    	
    	homePagePostViewModelList.add(homePagePostViewModel);
    }
    
    Collections.sort(homePagePostViewModelList);
    
    homePageViewModel.setPosts(homePagePostViewModelList);

    List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(userId);
    homePageViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
    
    return homePageViewModel;
  }
  
  public TopicInfo getTopicInfo(String topicName) {
  	TopicInfo topicInfo = topicInfoRepository.findOneBySubtopic(topicName);
  	
  	if (topicInfo == null) {
  		topicInfo = createAnEmptyTopicInfoObject();
  	}
  	
    return topicInfo;
  }
  
  public HomePageViewModel createAnEmptyHomePageViewModel() {
  	List<TopicInfo> topicInfoList = topicInfoRepository.findBySubtopic("all");
  	
  	User loggedInUser = userService.getLoggedInUser();
  	
  	TopicInfoSidebarViewModel topicInfoSidebarViewModel = topicMapper.mapTopicInfoToTopicInfoSidebarViewModel(topicInfoList.get(0), loggedInUser);
  	
  	HomePageViewModel homePageViewModel = new HomePageViewModel();
  	homePageViewModel.setTopicInfoSidebar(topicInfoSidebarViewModel);
  	
  	List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(loggedInUser.getId());
  	homePageViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
  	
  	return homePageViewModel;
  }
  
	private TopicInfo createAnEmptyTopicInfoObject() {
		TopicInfo topicInfo = new TopicInfo();
		
		topicInfo.setSubtopic("");
		topicInfo.setDescription("");
		topicInfo.setBackgroundImageUrl("");
		topicInfo.setRules("");
		topicInfo.setNavbarBackgroundColour("#000");
		topicInfo.setNavbarLinksColour("#000");
		topicInfo.setNavbarCurrentTopicColour("#000");
		topicInfo.setNavbarCurrentTopicBackgroundColour("#000");
		
		return topicInfo;
	}

  public void createNewTopic(TopicChangeModel topicModel) {
    TopicInfo topicInfo = topicMapper.mapTopicChangeModelToTopicInfo(topicModel);
    
    TopicInfo savedTopicInfo = topicInfoRepository.saveAndFlush(topicInfo);
    Long subtopicId = savedTopicInfo.getId();
    userSubtopicSubscriptionsService.manageTopicSubscription(subtopicId);
  }
  
  public void modifyExistingTopic(TopicChangeModel topicModel) {
  	TopicInfo topicInfo = topicInfoRepository.findOneBySubtopic(topicModel.getName());
  	
    topicMapper.mapTopicChangeModelToExistingTopicInfo(topicModel, topicInfo);
    topicInfoRepository.saveAndFlush(topicInfo);
  }
  
  public String getStyleSheetForTopic(String topicName) {

  	TopicInfo topicInfo = topicInfoRepository.findOneBySubtopic(topicName);
  	
  	String navbarBackgroundColour = topicInfo.getNavbarBackgroundColour();
  	String navbarLinksColour = topicInfo.getNavbarLinksColour();
  	String navbarCurrentTopicColour = topicInfo.getNavbarCurrentTopicColour();
  	String navbarCurrentTopicBackgroundColour = topicInfo.getNavbarCurrentTopicBackgroundColour();
  	String backgroundImageUrl = topicInfo.getBackgroundImageUrl();
  	
  	String styleSheet = 
  			new CssRulesBuilder(".navbar-inverse")
  				.addRule("background-color", navbarBackgroundColour)
  				.addRule("color", navbarLinksColour)
  				.newSelector(".navbar-nav > li > a")
  				.addRule("color", navbarLinksColour)
  				.newSelector(".navbar-inverse .navbar-text")
  				.addRule("color", navbarLinksColour)
  				.newSelector(".username a")
  				.addRule("color", navbarLinksColour)
  				.newSelector("#btnTopics")
  				.addRule("color", navbarLinksColour)
  				.newSelector(".navbar-inverse .navbar-nav > .active > a")
  				.addRule("color", navbarCurrentTopicColour)
  				.addRule("background-color", navbarCurrentTopicBackgroundColour)
  				.newSelector(".navbar-inverse .navbar-nav > .active > a:hover")
  				.addRule("opacity", "0.85")
  				.newSelector("#topicNavbar li a:hover")
  				.addRule("background-color", navbarCurrentTopicBackgroundColour)
  				.addRule("color", navbarCurrentTopicColour)
  				.newSelector("body")
  				.addRule("background", String.format("url('%s') no-repeat fixed", backgroundImageUrl))
  				.addRule("background-size", "cover")
  				.newSelector(".currentTopicDescription, .currentTopicRules, .currentTopicModerator, .currentTopicModeratorUsername, .globalSearchText")
  				.addRule("color", navbarLinksColour)
  				.newSelector(".currentTopicInfo p, .currentTopicName, .currentTopicFavourites")
  				.addRule("color", navbarLinksColour)
  				.newSelector("#btnNewPost")
  				.addRule("background-color", navbarCurrentTopicBackgroundColour)
  				.addRule("color", navbarCurrentTopicColour)
  				.newSelector("#btnNewPost:hover")
  				.addRule("background-color", navbarCurrentTopicBackgroundColour)
  				.addRule("opacity", "0.8")
  				.newSelector(".sidebarTitle")
  				.addRule("background", String.format("linear-gradient(to bottom, %s , %s)", navbarCurrentTopicBackgroundColour, navbarCurrentTopicBackgroundColour))
  				.addRule("color", navbarCurrentTopicColour)
  				.getStyleSheet();
  	
  	return styleSheet;
  }
  
}