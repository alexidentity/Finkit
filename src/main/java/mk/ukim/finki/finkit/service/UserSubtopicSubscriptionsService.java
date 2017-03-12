package mk.ukim.finki.finkit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.UserSubtopicSubscriptions;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.repositories.UserSubtopicSubscriptionsRepository;

@Service
public class UserSubtopicSubscriptionsService {
	
	private static final List<String> DEFAULT_TOPIC_SUBSCRIPTIONS =
			Arrays.asList("music", "news", "technology", "funny");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TopicInfoRepository topicInfoRepository;
	
	@Autowired
	private UserSubtopicSubscriptionsRepository userSubtopicSubscriptionsRepository;
	
	public boolean checkIfUserIsSubscribed(Long subtopicId) {
		Long userId = userService.getUserIdFromLoggedInUser();
		
		if (userId != null) {
			UserSubtopicSubscriptions userSubtopicSubscriptions =
					userSubtopicSubscriptionsRepository.findByUserIdAndSubtopicId(userId, subtopicId);
			
			if (userSubtopicSubscriptions != null) {
				return true;
			}
		}
		
		return false;
	}
	
	public long getNumberOfSubscribers(Long subtopicId) {
		long numberOfSubscribers = userSubtopicSubscriptionsRepository.countBySubtopicId(subtopicId);
		return numberOfSubscribers;
	}
	
	public List<String> getUserSubscriptions(Long userId) {
		List<String> subtopicSubscriptionList = new ArrayList<String>();
		
		if (userId != null) {
			List<UserSubtopicSubscriptions> userSubtopicSubscriptions = userSubtopicSubscriptionsRepository.findByUserId(userId);
			
			for (UserSubtopicSubscriptions subscription : userSubtopicSubscriptions) {
				TopicInfo topicInfo = topicInfoRepository.getOne(subscription.getSubtopicId());
				subtopicSubscriptionList.add(topicInfo.getSubtopic());
			}
			
			return subtopicSubscriptionList;
		}
		
		return DEFAULT_TOPIC_SUBSCRIPTIONS;
	}
	
	public void manageTopicSubscription(Long subtopicId) {
		Long userId = userService.getUserIdFromLoggedInUser();
		
		if (userId != null) {
			UserSubtopicSubscriptions userSubtopicSubscription = userSubtopicSubscriptionsRepository.findByUserIdAndSubtopicId(userId, subtopicId);
	    
	    if (userSubtopicSubscription != null) {
	    	userSubtopicSubscriptionsRepository.delete(userSubtopicSubscription);
	    } else {
	    	UserSubtopicSubscriptions userSubscription = new UserSubtopicSubscriptions();
	    	userSubscription.setUserId(userId);
	    	userSubscription.setSubtopicId(subtopicId);
	    	userSubtopicSubscriptionsRepository.save(userSubscription);
	    }
		}
	}
  
}