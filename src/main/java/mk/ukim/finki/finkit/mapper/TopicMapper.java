package mk.ukim.finki.finkit.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mk.ukim.finki.finkit.model.change.TopicChangeModel;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.view.TopicInfoSidebarViewModel;
import mk.ukim.finki.finkit.service.UserService;
import mk.ukim.finki.finkit.service.UserSubtopicSubscriptionsService;

@Component
public class TopicMapper {
  
  @Autowired
  private UserService userService;
  
  @Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;
  
  public TopicInfo mapTopicChangeModelToTopicInfo(TopicChangeModel topicModel) {

  	TopicInfo topicInfo = new TopicInfo();
    mapTopicChangeModelToExistingTopicInfo(topicModel, topicInfo);
    
    return topicInfo;
  }

	public void mapTopicChangeModelToExistingTopicInfo(
			TopicChangeModel topicModel, TopicInfo topicInfo) {
		
		String backgroundImageUrl = topicModel.getBackgroundImageUrl();
    topicInfo.setBackgroundImageUrl(backgroundImageUrl);
    
    String description = topicModel.getDescription();
    topicInfo.setDescription(description);
    
    String moderator = userService.getUsernameFromLoggedInUser();
    topicInfo.setModerator(moderator);
    
    String navbarBackgroundColour = topicModel.getNavbarBackgroundColour();
    topicInfo.setNavbarBackgroundColour(navbarBackgroundColour);
    
    String navbarCurrentTopicBackgroundColour = topicModel.getNavbarCurrentTopicBackgroundColour();
    topicInfo.setNavbarCurrentTopicBackgroundColour(navbarCurrentTopicBackgroundColour);
    
    String navbarCurrentTopicColour = topicModel.getNavbarCurrentTopicColour();
    topicInfo.setNavbarCurrentTopicColour(navbarCurrentTopicColour);
    
    String navbarLinksColour = topicModel.getNavbarLinksColour();
    topicInfo.setNavbarLinksColour(navbarLinksColour);
    
    String rules = topicModel.getRules();
    topicInfo.setRules(rules);
    
    String topicName = topicModel.getName();
    topicInfo.setSubtopic(topicName);
	}

  public TopicInfoSidebarViewModel mapTopicInfoToTopicInfoSidebarViewModel(TopicInfo topicInfo, User moderatorUser) {
    
    TopicInfoSidebarViewModel viewModel = new TopicInfoSidebarViewModel();
    
    Long id = topicInfo.getId();
    viewModel.setId(id);
    
    Long avatarId = moderatorUser.getAvatarId();
    viewModel.setAvatarId(avatarId);
    
    String description = topicInfo.getDescription();
    viewModel.setDescription(description);
    
    long numberOfSubscribers = userSubtopicSubscriptionsService.getNumberOfSubscribers(topicInfo.getId());
    viewModel.setFavouritedBy(numberOfSubscribers);
    
    String moderator = topicInfo.getModerator();
    viewModel.setModerator(moderator);
    
    String rules = topicInfo.getRules();
    viewModel.setRules(rules);
    
    String subtopic = topicInfo.getSubtopic();
    viewModel.setSubtopic(subtopic);
    
  	boolean isUserSubscribed = userSubtopicSubscriptionsService.checkIfUserIsSubscribed(id);
  	viewModel.setIsLoggedInUserSubscribed(isUserSubscribed);
    
    return viewModel;
  }
  
}
